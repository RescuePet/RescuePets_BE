package hanghae99.rescuepets.publicpet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicPetResponsDto {
    private Long id;//전체/상세
    private String desertionNo;//무조건 사용
    private String filename; //전체
    private String happenDt; //발생일자
    private String happenPlace; //발생 장소
    private String kindCd; //동물 종류 [개] 믹스견,[고양이] 한국고양이, [기타축종] 사향오리 /전체/상세
    private String ageWeightNeuterYn; //전체 중성화 O/2023년생/0.3(Kg) //상세 중성화 O/2023년생/0.3(Kg)/검정
    private String ageWeightNeuterYnColorCd; //전체 중성화 O/2023년생/0.3(Kg)//상세 중성화 O/2023년생/0.3(Kg)/검정
    private String noticeNo; //공고 번호
    private String noticeDate;//전체/상세 noticeSdt~noticeEdt 공고 일자 합함
    private String popfile;//상세
    private String processState; //상세
    private String sexCd; //전체/상세
    private String specialMark;//상세
    private String careNm;//보호소 이름 /전체
    private String careTel; //상세
    private String careAddr;//상세
    private String orgNm; //상세
    private String chargeNm; //담당자 이름
    private String officetel;//상세
    private String state;//공고, 보호, 종료 /전체/상세
    private Boolean isScrap; //전체/상세
    private Integer scrapCount;//상세
    private Boolean isInquiry;//상세
    private Integer InquiryCount;//상세

    public static PublicPetResponsDto of(PetInfoByAPI petInfoByAPI, Boolean isScrap) {
        String neuterYn = neuterYnChange(petInfoByAPI.getNeuterYn());
        return PublicPetResponsDto.builder()
                .id(petInfoByAPI.getId())
                .desertionNo(petInfoByAPI.getDesertionNo())
                .filename(petInfoByAPI.getFilename())
                .kindCd(petInfoByAPI.getKindCd())
                .ageWeightNeuterYn("중성화"+neuterYn+"/"+petInfoByAPI.getAge()+"/"+petInfoByAPI.getWeight())
                .noticeDate(petInfoByAPI.getNoticeSdt()+"~"+petInfoByAPI.getNoticeEdt())//공고 시작일 종료일 합침
                .sexCd(petInfoByAPI.getSexCd())
                .careNm(petInfoByAPI.getCareNm())//사용
                .officetel(petInfoByAPI.getOfficetel())
                .state(petInfoByAPI.getPetStateEnum().getKorean())
                .isScrap(isScrap)
                .build();
    }
    public static PublicPetResponsDto of(PetInfoByAPI petInfoByAPI, Boolean isScrap, Integer scrapCount, Boolean isInquiry, Integer InquiryCount) {
        String neuterYn = neuterYnChange(petInfoByAPI.getNeuterYn());
        return PublicPetResponsDto.builder()
                .id(petInfoByAPI.getId())
                .desertionNo(petInfoByAPI.getDesertionNo())
                .filename(petInfoByAPI.getFilename())
                .happenDt(petInfoByAPI.getHappenDt())
                .happenPlace(petInfoByAPI.getHappenPlace())
                .kindCd(petInfoByAPI.getKindCd())
                .ageWeightNeuterYnColorCd("중성화"+neuterYn+"/"+petInfoByAPI.getAge()+"/"+petInfoByAPI.getWeight()+"/"+petInfoByAPI.getColorCd())
                .noticeNo(petInfoByAPI.getNoticeNo())
                .noticeDate(petInfoByAPI.getNoticeSdt()+"~"+petInfoByAPI.getNoticeEdt())//공고 시작일 종료일 합침
                .popfile(petInfoByAPI.getPopfile())
                .processState(petInfoByAPI.getProcessState())
                .sexCd(petInfoByAPI.getSexCd())
                .specialMark(petInfoByAPI.getSpecialMark())
                .careNm(petInfoByAPI.getCareNm())
                .careTel(petInfoByAPI.getCareTel())
                .careAddr(petInfoByAPI.getCareAddr())
                .orgNm(petInfoByAPI.getOrgNm())
                .chargeNm(petInfoByAPI.getChargeNm())
                .officetel(petInfoByAPI.getOfficetel())
                .state(petInfoByAPI.getPetStateEnum().getKorean())
                .isScrap(isScrap)
                .scrapCount(scrapCount)
                .isInquiry(isInquiry)
                .InquiryCount(InquiryCount)
                .build();
    }

    private static String neuterYnChange(String neuterYn){
        switch (neuterYn) {
            case "Y" -> neuterYn = "O";
            case "N" -> neuterYn = "X";
            case "U" -> neuterYn = "?";
        }
        return neuterYn;
    }
}
