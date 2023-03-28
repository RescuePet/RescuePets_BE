package hanghae99.rescuepets.publicpet.dto;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicPetResponsDto {
    private Long id;
    private String desertionNo;
    private String filename;
    private String happenDt;
    private String happenPlace;
    private String kindCd;
    private String colorCd;
    private String age;
    private String weight;
    private String noticeNo;
    private String noticeSdt;
    private String noticeEdt;
    private String popfile;
    private String processState;
    private String sexCd;
    private String neuterYn;
    private String specialMark;
    private String careNm;
    private String careTel;
    private String careAddr;
    private String orgNm;
    private String chargeNm;
    private String officetel;
    private String state;
    private Boolean isScrap;
    private Integer scrapCount;
    public static PublicPetResponsDto of(PetInfoByAPI petInfoByAPI, Boolean isScrap, Integer scrapCount) {
        return PublicPetResponsDto.builder()
                .id(petInfoByAPI.getId())
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
                .state(petInfoByAPI.getPetStateEnum().getKorean())
                .isScrap(isScrap)
                .scrapCount(scrapCount)
                .build();
    }
}
