package hanghae99.rescuepets.publicpet.data;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.publicpet.repository.PublicPetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiScheduler {
    @Value("${public.api.key}")
    private String publicApiKey;
    private final PublicPetRepository publicPetRepository;


    //3분 마다 실행
    //현재 실행 결과 소요시간 ={1차: 326434ms 2차:286378ms(업데이트만) 3차:278525ms(저장만)}
//    @Scheduled(cron = "0 0/3 * * * *")
    @Transactional
    public void testSchedule() throws IOException {
        long startTime = System.currentTimeMillis();//시작 시간
        String[] state = {"", "protect", "notice", "end"};
        int stateNo = 0;
        int pageNo = 0;
        while (stateNo < 3) {
            pageNo++;
            String apiUrl = createApiUrlTest(String.valueOf(pageNo), state[stateNo]);
            JSONArray itemList = fetchDataFromApi(apiUrl);
            if (itemList == null || itemList.isEmpty()) {
                log.info("State: " + state[stateNo] + ",  pageNo: " + pageNo + "을 API로부터 데이터를 받아오지 못했습니다.-------------------------------------------------------------------------");
                stateNo++;
                pageNo = 0;
                continue;
            }
            saveAndUpdateToDatabase(itemList, state[stateNo]);
        }
        long endTime = System.currentTimeMillis(); //종료 시간
        long executionTime = endTime - startTime; //소요 시간 계산
        log.info("Execution time: " + executionTime + "ms");
    }

    private String createApiUrlTest(String pageNo, String state) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + publicApiKey);
        urlBuilder.append("&" + URLEncoder.encode("bgnde", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 시작일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("endde", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 종료일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("upkind", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900)*/
        urlBuilder.append("&" + URLEncoder.encode("kind", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*품종코드 (품종 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("upr_cd", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도코드 (시도 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("org_cd", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구코드 (시군구 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("care_reg_no", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보호소번호 (보호소 조회 OPEN API 참조)*/

        if (state.equals("protect")) {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("protect", "UTF-8"));
        } else if (state.equals("notice")) {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("notice", "UTF-8"));
        } else {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
        }

        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*페이지당 보여줄 개수 (1,000 이하), 기본값 : 10*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/

        return urlBuilder.toString();
    }

    private JSONArray fetchDataFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            log.info(new BufferedReader(new InputStreamReader(conn.getErrorStream())) + "");
            throw new IOException();
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // JSON 데이터 파싱 및 추출
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.optJSONObject("items");
        JSONArray itemList = null;
        if (!items.isEmpty()) {
            itemList = items.getJSONArray("item");
        }
        return itemList;
    }

    private void saveAndUpdateToDatabase(JSONArray itemList, String state) {
        for (int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);
            Optional<PetInfoByAPI> petInfoByAPIOptional = publicPetRepository.findByDesertionNo(itemObject.optString("desertionNo"));
            PetInfoByAPI petInfoByAPI = petInfoByAPIOptional.orElse(null);
            if (petInfoByAPIOptional.isEmpty()) {
                log.info("saveAndUpdateToDatabase 메서드에서 if문 save 동작함");
                PetInfoByAPI petInfo = buildPetInfoTest(itemObject, state);
                publicPetRepository.save(petInfo);
            } else if (petInfoByAPI == null) { //이부분 조금 더 생각해볼 필요 있음. if문 진행 관련
                log.info("saveAndUpdateToDatabase 메서드에서 if문 null 동작함");
            } else {
                log.info("saveAndUpdateToDatabase 메서드에서 update 동작함");
                PetInfoByAPI petInfo = buildPetInfoTest(itemObject, state);
                petInfoByAPI.update(petInfo); //update 수정 고려
            }
        }
    }

    private PetInfoByAPI buildPetInfoTest(JSONObject itemObject, String state) {
        return PetInfoByAPI.builder()
                .desertionNo(itemObject.optString("desertionNo"))
                .filename(itemObject.optString("filename"))
                .happenDt(itemObject.optString("happenDt"))
                .happenPlace(itemObject.optString("happenPlace"))
                .kindCd(itemObject.optString("kindCd"))
                .colorCd(itemObject.optString("colorCd"))
                .age(itemObject.optString("age"))
                .weight(itemObject.optString("weight"))
                .noticeNo(itemObject.optString("noticeNo"))
                .noticeSdt(itemObject.optString("noticeSdt"))
                .noticeEdt(itemObject.optString("noticeEdt"))
                .popfile(itemObject.optString("popfile"))
                .processState(itemObject.optString("processState"))
                .sexCd(itemObject.optString("sexCd"))
                .neuterYn(itemObject.optString("neuterYn"))
                .specialMark(itemObject.optString("specialMark"))
                .careNm(itemObject.optString("careNm"))
                .careTel(itemObject.optString("careTel"))
                .careAddr(itemObject.optString("careAddr"))
                .orgNm(itemObject.optString("orgNm"))
                .chargeNm(itemObject.optString("chargeNm"))
                .officetel(itemObject.optString("officetel"))
                .state(state)
                .build();
    }


}
