package hanghae99.rescuepets.common.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetInfoByAPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String status;

    public PetInfoByAPI(PetInfoByAPI petInfo) {
        this.desertionNo = petInfo.desertionNo;
        this.filename = petInfo.filename;
        this.happenDt = petInfo.happenDt;
        this.happenPlace = petInfo.happenPlace;
        this.kindCd = petInfo.kindCd;
        this.colorCd = petInfo.colorCd;
        this.age = petInfo.age;
        this.weight = petInfo.weight;
        this.noticeNo = petInfo.noticeNo;
        this.noticeSdt = petInfo.noticeSdt;
        this.noticeEdt = petInfo.noticeEdt;
        this.popfile = petInfo.popfile;
        this.processState = petInfo.processState;
        this.sexCd = petInfo.sexCd;
        this.neuterYn = petInfo.neuterYn;
        this.specialMark = petInfo.specialMark;
        this.careNm = petInfo.careNm;
        this.careTel = petInfo.careTel;
        this.careAddr = petInfo.careAddr;
        this.orgNm = petInfo.orgNm;
        this.chargeNm = petInfo.chargeNm;
        this.officetel = petInfo.officetel;
        this.status = petInfo.status;
    }
}
