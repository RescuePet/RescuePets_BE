package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.PetInfoLike;
import hanghae99.rescuepets.common.entity.PetInfoState;
import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import hanghae99.rescuepets.publicpet.repository.PetInfoLikeRepository;
import hanghae99.rescuepets.publicpet.repository.PetInfoStateRepository;
import hanghae99.rescuepets.publicpet.repository.PublicPetRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicPetService {
    @Value("${public.api.key}")
    private String publicApiKey;
    private final PublicPetRepository publicPetRepository;
    private final PetInfoLikeRepository petInfoLikeRepository;
    private final PetInfoStateRepository petInfoStateRepository;


    @Transactional
    public String apiSaves(String pageNo, String state) throws IOException {
        String apiUrl = createApiUrl(pageNo, state);
        JSONArray itemList = fetchDataFromApi(apiUrl);
        saveDataToDatabase(itemList, state);
        return "성공";
    }

    private String createApiUrl(String pageNo, String state) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + publicApiKey);
        urlBuilder.append("&" + URLEncoder.encode("bgnde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 시작일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("endde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 종료일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("upkind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900)*/
        urlBuilder.append("&" + URLEncoder.encode("kind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*품종코드 (품종 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도코드 (시도 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("org_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구코드 (시군구 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("care_reg_no","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보호소번호 (보호소 조회 OPEN API 참조)*/

        if (state.equals("protect")) {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("protect", "UTF-8"));
        } else if (state.equals("notice")) {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("notice", "UTF-8"));
        } else {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
        }

        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*페이지당 보여줄 개수 (1,000 이하), 기본값 : 10*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/

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
        JSONObject items = body.getJSONObject("items");
        JSONArray itemList = items.getJSONArray("item");

        return itemList;
    }

    private void saveDataToDatabase(JSONArray itemList, String state) {
        for (int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);

            if (state.equals("")) {
                PetInfoByAPI petInfo = buildPetInfoByAPI(itemObject);
                publicPetRepository.save(petInfo);
            } else {
                PetInfoState petInfo = buildPetInfoState(itemObject, state);
                petInfoStateRepository.save(petInfo);
            }
        }
    }

    private PetInfoByAPI buildPetInfoByAPI(JSONObject itemObject) {
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
                .build();
    }

    private PetInfoState buildPetInfoState(JSONObject itemObject, String state) {
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
                .build();
    }

    //------------------------------위는 메서드 분리 작업 완료-----------------------------------------------
    //apiSave는 테스트 완료시 분리 예정 @Scheduled
    //전체 저장
    @Transactional
    public String apiSave(String pageNo, String state) throws IOException {
        //추후 메서드 빼는거 고려 / 필요한 값은 변수명 지정하여 중복 코드 수정필요할것으로 생각됨.
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + publicApiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("bgnde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 시작일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("endde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 종료일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("upkind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900)*/
        urlBuilder.append("&" + URLEncoder.encode("kind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*품종코드 (품종 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도코드 (시도 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("org_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구코드 (시군구 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("care_reg_no","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보호소번호 (보호소 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("state","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*상태(전체 : null(빈값), 공고중 : notice, 보호중 : protect)*/
        urlBuilder.append("&" + URLEncoder.encode("neuter_yn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*상태 (전체 : null(빈값), 예 : Y, 아니오 : N, 미상 : U)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지 번호 (기본값 : 1)*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지당 보여줄 개수 (1,000 이하), 기본값 : 10*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        JSONObject items = body.getJSONObject("items");
        JSONArray itemList = items.getJSONArray("item");

        // itemList에 있는 데이터를 추출하여 데이터베이스에 저장
        for(int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);

            PetInfoByAPI petInfo = PetInfoByAPI.builder()
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
                    .build();
            publicPetRepository.save(petInfo);
        }
        return "성공";
    }

    //보호중 Protect 저장
    @Transactional
    public String apiSaveProtect(String pageNo, String state) throws IOException {
        //추후 메서드 빼는거 고려 / 필요한 값은 변수명 지정하여 중복 코드 수정필요할것으로 생각됨.
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + publicApiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("bgnde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 시작일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("endde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 종료일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("upkind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900)*/
        urlBuilder.append("&" + URLEncoder.encode("kind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*품종코드 (품종 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도코드 (시도 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("org_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구코드 (시군구 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("care_reg_no","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보호소번호 (보호소 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("state","UTF-8") + "=" + URLEncoder.encode("protect", "UTF-8")); /*상태(전체 : null(빈값), 공고중 : notice, 보호중 : protect)*/
        urlBuilder.append("&" + URLEncoder.encode("neuter_yn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*상태 (전체 : null(빈값), 예 : Y, 아니오 : N, 미상 : U)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지 번호 (기본값 : 1)*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지당 보여줄 개수 (1,000 이하), 기본값 : 10*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        JSONObject items = body.getJSONObject("items");
        JSONArray itemList = items.getJSONArray("item");

        // itemList에 있는 데이터를 추출하여 데이터베이스에 저장
        for(int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);

            PetInfoState petInfo = PetInfoState.builder()
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
                    .state("protect")
                    .build();
            petInfoStateRepository.save(petInfo);
        }
        return "성공";
    }


    @Transactional
    public String apiSaveNotice(String pageNo, String state) throws IOException {
        //추후 메서드 빼는거 고려 / 필요한 값은 변수명 지정하여 중복 코드 수정필요할것으로 생각됨.
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + publicApiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("bgnde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 시작일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("endde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*유기날짜(검색 종료일) (YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("upkind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*축종코드 (개 : 417000, 고양이 : 422400, 기타 : 429900)*/
        urlBuilder.append("&" + URLEncoder.encode("kind","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*품종코드 (품종 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("upr_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도코드 (시도 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("org_cd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구코드 (시군구 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("care_reg_no","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보호소번호 (보호소 조회 OPEN API 참조)*/
        urlBuilder.append("&" + URLEncoder.encode("state","UTF-8") + "=" + URLEncoder.encode("notice", "UTF-8")); /*상태(전체 : null(빈값), 공고중 : notice, 보호중 : protect)*/
        urlBuilder.append("&" + URLEncoder.encode("neuter_yn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*상태 (전체 : null(빈값), 예 : Y, 아니오 : N, 미상 : U)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지 번호 (기본값 : 1)*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지당 보여줄 개수 (1,000 이하), 기본값 : 10*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        JSONObject items = body.getJSONObject("items");
        JSONArray itemList = items.getJSONArray("item");

        // itemList에 있는 데이터를 추출하여 데이터베이스에 저장
        for(int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);

            PetInfoState petInfo = PetInfoState.builder()
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
                    .state("notice")
                    .build();
            petInfoStateRepository.save(petInfo);
        }
        return "성공";
    }


    @Transactional(readOnly = true)
    public List<PublicPetResponsDto> getPublicPet(int page, int size, String sortBy) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetInfoByAPI> postPage = publicPetRepository.findAll(pageable);

        List<PetInfoByAPI> pets = postPage.getContent();
        List<PublicPetResponsDto> dtoList = new ArrayList<>();

        for (PetInfoByAPI petInfoByAPI : pets) {
            PublicPetResponsDto responseDto = PublicPetResponsDto.of(petInfoByAPI);
            dtoList.add(responseDto);
        }
    return dtoList;
    }


    @Transactional(readOnly = true)
    public PublicPetResponsDto getPublicPetDetails(String desertionNo) {
        PetInfoByAPI petInfoByAPI = getPetInfo(desertionNo);
        return PublicPetResponsDto.of(petInfoByAPI);
    }

    //관심 유기동물 등록
    @Transactional
    public String petInfoLike(String desertionNo, Member member) {
        getPetInfo(desertionNo);
        if (petInfoLikeRepository.findByMemberIdAndDesertionNo(member.getId(),desertionNo).isPresent()){
            //이미 등록한 상태
            log.error("이미 관심 등록되어 있습니다.");
            throw new RuntimeException();
        }
        petInfoLikeRepository.save(new PetInfoLike(member, desertionNo));
        return "성공";
    }

    //관심 유기동물 삭제
    @Transactional
    public String deletePetInfoLike(Long PetInfoLikeId, Member member) {
        if (petInfoLikeRepository.findByMemberIdAndId(member.getId(), PetInfoLikeId).isEmpty()){
            log.error("요청하신 유저는 해당 관심 유기 동물이 등록되어 있지 않습니다.");
            throw new NullPointerException();
        }
        petInfoLikeRepository.deleteByMemberIdAndId(member.getId(), PetInfoLikeId);
        return "성공";
    }

    private PetInfoByAPI getPetInfo(String desertionNo) {
        return publicPetRepository.findByDesertionNo(desertionNo).orElseThrow(
                () -> new NullPointerException("해당 유기동물 정보가 없습니다.")
        );
    }
}
