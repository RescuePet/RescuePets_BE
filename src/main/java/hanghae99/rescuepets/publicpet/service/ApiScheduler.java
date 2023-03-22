package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.PetInfoState;
import hanghae99.rescuepets.publicpet.repository.PetInfoStateRepository;
import hanghae99.rescuepets.publicpet.repository.PublicPetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiScheduler {
    @Value("${public.api.key}")
    private String publicApiKey;
    private final PublicPetRepository publicPetRepository;
    private final PetInfoStateRepository petInfoStateRepository;


    //30분 마다 실행
    //현재 실행 결과 소요시간 ={1차: 326434ms 2차:286378ms(업데이트만) 3차:278525ms(저장만)}
//    @Scheduled(cron = "0 0/30 * * * *")
    @Transactional
    protected void ApiSchedule() throws IOException {
        long startTime = System.currentTimeMillis();//시작 시간
        String[] state = {"", "protect", "notice", "end"};
        int stateNo = 0;
        int pageNo = 0;
        String size = "1000";
        while (stateNo < 3) {
            pageNo++;
            String apiUrl = createApiUrl(String.valueOf(pageNo), state[stateNo], size);
            JSONArray itemList = fetchDataFromApi(apiUrl);
            if (itemList == null || itemList.isEmpty()) {
                log.info("State: " + state[stateNo] + ",  pageNo: " + pageNo + "을 API로부터 데이터를 받아오지 못했습니다.-------------------------------------------------------------------------");
                stateNo++;
                pageNo = 0;
                continue;
            }
            saveOrUpdateFromApi(itemList, state[stateNo]);
        }
        long endTime = System.currentTimeMillis(); //종료 시간
        long executionTime = endTime - startTime; //소요 시간 계산
        log.info("-------------------------Execution time: " + executionTime + "ms");
    }

    @Scheduled(cron = "0 0/30 * * * *")
    @Transactional
    protected void ApiScheduleTest() throws IOException {
        long startTime = System.currentTimeMillis();//시작 시간
        String[] state = {"protect", "notice", "", "end"};
        int stateNo = 0;
        int pageNo = 0;
        String size = "1000";
        while (stateNo < 3) {
            pageNo++;
            String apiUrl = createApiUrl(String.valueOf(pageNo), state[stateNo], size);
            JSONArray itemList = fetchDataFromApi(apiUrl);
            if (itemList == null || itemList.isEmpty()) {
                log.info("State: " + state[stateNo] + ",  pageNo: " + pageNo + "을 API로부터 데이터를 받아오지 못했습니다.-------------------------------------------------------------------------");
                stateNo++;
                pageNo = 0;
                continue;
            }
            compareData(itemList, state[stateNo]);
//            log.info(state[stateNo].toString() + "/ pageNo: "+pageNo +  "  완료");
        }
        long endTime = System.currentTimeMillis(); //종료 시간
        long executionTime = endTime - startTime; //소요 시간 계산
        log.info("-------------------------Execution time: " + executionTime + "ms");
    }

    protected String createApiUrl(String pageNo, String state, String size) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + publicApiKey);
        urlBuilder.append("&" + URLEncoder.encode("bgnde", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 시작일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("endde", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 종료일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("upkind", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900)*/
        urlBuilder.append("&" + URLEncoder.encode("kind", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*품종코드 (품종 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("upr_cd", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도코드 (시도 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("org_cd", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구코드 (시군구 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("care_reg_no", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보호소번호 (보호소 조회 OPEN API 참조)*/
        //인증키를 제외하고 url윗 부분 생략 가능.
        if (state.equals("protect")) {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("protect", "UTF-8"));
        } else if (state.equals("notice")) {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("notice", "UTF-8"));
        } else {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
        }

        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(size, "UTF-8")); /*페이지당 보여줄 개수 (1,000 이하), 기본값 : 10*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/

        return urlBuilder.toString();
    }

    protected JSONArray fetchDataFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
//        log.info("Response code: " + conn.getResponseCode());
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

    protected void saveOrUpdateFromApi(JSONArray itemList, String state) {
        for (int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);
            Optional<PetInfoByAPI> petInfoByAPIOptional = publicPetRepository.findByDesertionNo(itemObject.optString("desertionNo"));
            PetInfoByAPI petInfoByAPI = petInfoByAPIOptional.orElse(null);
            if (petInfoByAPIOptional.isEmpty()) {
//                log.info("saveAndUpdateToDatabase 메서드 save 동작");
                PetInfoByAPI petInfo = buildPetInfo(itemObject, state);
                publicPetRepository.save(petInfo);
            } else {
//                log.info("saveAndUpdateToDatabase 메서드 update 동작");
                PetInfoByAPI petInfo = buildPetInfo(itemObject, state);
                petInfoByAPI.update(petInfo);
            }
        }
    }


    protected PetInfoByAPI buildPetInfo(JSONObject itemObject, String state) {
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

    //변동 내용 저장용

    //    compareData
    protected void compareData(JSONArray itemList, String state) {
        for (int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);
            Optional<PetInfoByAPI> petInfoByAPIOptional = publicPetRepository.findByDesertionNo(itemObject.optString("desertionNo"));
            List<String> compareDataList = new ArrayList<>();
            if (petInfoByAPIOptional.isEmpty()) {
//                log.info("saveAndUpdateToDatabase 메서드 save 동작");
                PetInfoByAPI petInfo = buildPetInfo(itemObject, state);
                publicPetRepository.save(petInfo);
            } else {
                PetInfoByAPI petInfoByAPI = petInfoByAPIOptional.orElse(null);
//                log.info("saveAndUpdateToDatabase 메서드 update 동작");
                if (!petInfoByAPI.getFilename().equals(itemObject.optString("filename"))) {
                    compareDataList.add("filename");
                }
                if (!petInfoByAPI.getHappenDt().equals(itemObject.optString("happenDt"))) {
                    compareDataList.add("happenDt");
                }
                if (!petInfoByAPI.getHappenPlace().equals(itemObject.optString("happenPlace"))) {
                    compareDataList.add("happenPlace");
                }
                if (!petInfoByAPI.getKindCd().equals(itemObject.optString("kindCd"))) {
                    compareDataList.add("kindCd");
                }
                if (!petInfoByAPI.getColorCd().equals(itemObject.optString("colorCd"))) {
                    compareDataList.add("colorCd");
                }
                if (!petInfoByAPI.getAge().equals(itemObject.optString("age"))) {
                    compareDataList.add("age");
                }
                if (!petInfoByAPI.getWeight().equals(itemObject.optString("weight"))) {
                    compareDataList.add("weight");
                }
                if (!petInfoByAPI.getNoticeNo().equals(itemObject.optString("noticeNo"))) {
                    compareDataList.add("noticeNo");
                }
                if (!petInfoByAPI.getNoticeSdt().equals(itemObject.optString("noticeSdt"))) {
                    compareDataList.add("noticeSdt");
                }
                if (!petInfoByAPI.getNoticeEdt().equals(itemObject.optString("noticeEdt"))) {
                    compareDataList.add("noticeEdt");
                }
                if (!petInfoByAPI.getPopfile().equals(itemObject.optString("popfile"))) {
                    compareDataList.add("popfile");
                }
                if (!petInfoByAPI.getProcessState().equals(itemObject.optString("processState"))) {
                    compareDataList.add("processState");
                }
                if (!petInfoByAPI.getSexCd().equals(itemObject.optString("sexCd"))) {
                    compareDataList.add("sexCd");
                }
                if (!petInfoByAPI.getNeuterYn().equals(itemObject.optString("neuterYn"))) {
                    compareDataList.add("neuterYn");
                }
                if (!petInfoByAPI.getSpecialMark().equals(itemObject.optString("specialMark"))) {
                    compareDataList.add("specialMark");
                }
                if (!petInfoByAPI.getCareNm().equals(itemObject.optString("careNm"))) {
                    compareDataList.add("careNm");
                }
                if (!petInfoByAPI.getCareTel().equals(itemObject.optString("careTel"))) {
                    compareDataList.add("careTel");
                }
                if (!petInfoByAPI.getCareAddr().equals(itemObject.optString("careAddr"))) {
                    compareDataList.add("careAddr");
                }
                if (!petInfoByAPI.getOrgNm().equals(itemObject.optString("orgNm"))) {
                    compareDataList.add("orgNm");
                }
                if (!petInfoByAPI.getChargeNm().equals(itemObject.optString("chargeNm"))) {
                    compareDataList.add("chargeNm");
                }
                if (!petInfoByAPI.getOfficetel().equals(itemObject.optString("officetel"))) {
                    compareDataList.add("officetel");
                }
                if (!petInfoByAPI.getState().equals(state)) { //state가 다를 경우 true
                    if (state.equals("") && itemObject.optString("getProcessState").contains("종료")) { //json 요청 state가 ""일 때 getProcessState도 종료 상태라면 데이터베이스 수정 요청
                        compareDataList.add("state");
//                        log.info("수정 동작 --- state == null");
                    }
                    if ((state.equals("protect") || state.equals("notice")) && !itemObject.optString("getProcessState").contains("보호")) {
                        compareDataList.add("state");
//                        log.info("수정 동작 --- state == protect/notice");
                    }
                }
                if (!compareDataList.isEmpty()) {
                    String compareDataKey = String.join(", ", compareDataList);
                    PetInfoByAPI petInfo = buildPetInfo(itemObject, state);
                    PetInfoState petInfoEntity = buildPetInfoApi(itemObject, state, compareDataKey);
                    PetInfoState entityPetInfo = buildPetInfoEntity(petInfoByAPI, state, compareDataKey);
                    petInfoByAPI.update(petInfo);
                    petInfoStateRepository.save(petInfoEntity);
                    petInfoStateRepository.save(entityPetInfo);
//                    log.info("현재시간: " + LocalTime.now() + "/ desertionNo 및 변경사항: :" + itemObject.optString("desertionNo") + "/ " + compareDataKey + "-------------------------------------------------------------------------");
                }
                //list가 비었을 경우 변동 사항이 없으므로 업데이트 동작하지 않음
            }
//            log.info("compareDataList 비었는지 체크 true(null),false(값이 있음):" + compareDataList.isEmpty() + "");
        }
    }

    protected PetInfoState buildPetInfoApi(JSONObject itemObject, String state, String compareDataKey) {
        return PetInfoState.builder()
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
                .objectType("Json")
                .compareDataKey(compareDataKey)
                .build();
    }

    protected PetInfoState buildPetInfoEntity(PetInfoByAPI petInfoByAPI, String state, String compareDataKey) {
        return PetInfoState.builder()
                .desertionNo(petInfoByAPI.getDesertionNo())
                .filename(petInfoByAPI.getFilename())
                .happenDt(petInfoByAPI.getHappenDt())
                .happenPlace(petInfoByAPI.getHappenPlace())
                .kindCd(petInfoByAPI.getKindCd())
                .colorCd(petInfoByAPI.getColorCd())
                .age(petInfoByAPI.getAge())
                .weight(petInfoByAPI.getWeight())
                .noticeNo(petInfoByAPI.getNoticeNo())
                .noticeSdt(petInfoByAPI.getNoticeSdt())
                .noticeEdt(petInfoByAPI.getNoticeEdt())
                .popfile(petInfoByAPI.getPopfile())
                .processState(petInfoByAPI.getProcessState())
                .sexCd(petInfoByAPI.getSexCd())
                .neuterYn(petInfoByAPI.getNeuterYn())
                .specialMark(petInfoByAPI.getSpecialMark())
                .careNm(petInfoByAPI.getCareNm())
                .careTel(petInfoByAPI.getCareTel())
                .careAddr(petInfoByAPI.getCareAddr())
                .orgNm(petInfoByAPI.getOrgNm())
                .chargeNm(petInfoByAPI.getChargeNm())
                .officetel(petInfoByAPI.getOfficetel())
                .state(petInfoByAPI.getState())
                .objectType("Entity")
                .compareDataKey(compareDataKey)
                .build();
    }
}
