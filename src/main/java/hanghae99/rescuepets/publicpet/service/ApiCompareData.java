package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.publicpet.repository.PublicPetInfoRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hanghae99.rescuepets.common.entity.PetStateEnum.*;

@Component
@RequiredArgsConstructor
public class ApiCompareData {

    private final PublicPetInfoRepository publicPetInfoRepository;
    private final ApiClient apiClient;
//    private final SseService sseService;
//    private final ScrapRepository scrapRepository;

    @Transactional
    protected void compareData(JSONArray itemList, PetStateEnum state) {
        for (int i = 0; i < itemList.length(); i++) {
            JSONObject itemObject = itemList.getJSONObject(i);
            Optional<PetInfoByAPI> petInfoByAPIOptional = publicPetInfoRepository.findByDesertionNo(itemObject.optLong("desertionNo"));
            List<String> compareDataList = new ArrayList<>();
            if (petInfoByAPIOptional.isEmpty()) {
                String careAddr = itemObject.optString("careAddr");
                PetInfoByAPI petInfo = PetInfoByAPI.of(itemObject, state);
                List<Double> latitudeandlongitude = apiClient.fetchLatLngFromKakaoApi(careAddr);
                if (latitudeandlongitude != null) {
                    petInfo.location(latitudeandlongitude.get(0), latitudeandlongitude.get(1), true);
                } else {
                    String[] careAddrParts = careAddr.split(" ");
                    if (careAddrParts.length >= 1) {
                        careAddr = careAddrParts[0] + " " + careAddrParts[1];
                        latitudeandlongitude = apiClient.fetchLatLngFromKakaoApi(careAddr);
                        petInfo.location(latitudeandlongitude.get(0), latitudeandlongitude.get(1), false);
                    }
                }
                publicPetInfoRepository.save(petInfo);

            } else {
                PetInfoByAPI petInfoByAPI = petInfoByAPIOptional.get();
                Field[] fields = petInfoByAPI.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = null;
                    try {
                        value = field.get(petInfoByAPI);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (itemObject.has(name)) {
                        if (!value.equals(itemObject.optString(name))) {
                            compareDataList.add(name);
                        }
                    }
//                    log.info(field.getName() + ": " + value);
                }
                if (!petInfoByAPI.getPetStateEnum().equals(state)) {
                    if (state.equals(END) && itemObject.optString("processState").contains("종료")) { //json 요청 state가 ""일 때 getProcessState도 종료 상태라면 데이터베이스 수정 요청
                        compareDataList.add("state");
                    }
                    if ((state.equals(PROTECT) || state.equals(NOTICE)) && itemObject.optString("processState").contains("보호")) {
                        compareDataList.add("state");
                    }
                }
                if (!compareDataList.isEmpty()) {
                    petInfoByAPI.update(itemObject, state);
//                    publicPetInfoRepository.saveAndFlush(petInfoByAPI);
//                    List<Scrap> scrapList = scrapRepository.findByPetInfoByAPI_desertionNo(petInfoByAPI.getDesertionNo());
//                    for (Scrap scrap : scrapList) {
//                        sseService.send(scrap.getMember(), NotificationType.UPDATE, "회원님이 스크랩한 게시물 정보가 업데이트 되었습니다.");
                    }
                }
            }
        }
    }

//    private PetInfoByAPI buildPetInfo(JSONObject itemObject, PetStateEnum state) {
//        return PetInfoByAPI.builder()
//                .desertionNo(itemObject.optString("desertionNo"))
//                .filename(itemObject.optString("filename"))
//                .happenDt(itemObject.optString("happenDt"))
//                .happenPlace(itemObject.optString("happenPlace"))
//                .kindCd(itemObject.optString("kindCd"))
//                .colorCd(itemObject.optString("colorCd"))
//                .age(itemObject.optString("age"))
//                .weight(itemObject.optString("weight"))
//                .noticeNo(itemObject.optString("noticeNo"))
//                .noticeSdt(itemObject.optString("noticeSdt"))
//                .noticeEdt(itemObject.optString("noticeEdt"))
//                .popfile(itemObject.optString("popfile"))
//                .processState(itemObject.optString("processState"))
//                .sexCd(itemObject.optString("sexCd"))
//                .neuterYn(itemObject.optString("neuterYn"))
//                .specialMark(itemObject.optString("specialMark"))
//                .careNm(itemObject.optString("careNm"))
//                .careTel(itemObject.optString("careTel"))
//                .careAddr(itemObject.optString("careAddr"))
//                .orgNm(itemObject.optString("orgNm"))
//                .chargeNm(itemObject.optString("chargeNm"))
//                .officetel(itemObject.optString("officetel"))
//                .petStateEnum(state)
//                .build();
//    }
//}
