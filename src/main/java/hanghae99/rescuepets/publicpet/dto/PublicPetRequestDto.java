package hanghae99.rescuepets.publicpet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicPetRequestDto {
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
    private String status; //지정 해줘야 함/ 고려할것

}
