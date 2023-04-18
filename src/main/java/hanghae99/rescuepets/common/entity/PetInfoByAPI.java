package hanghae99.rescuepets.common.entity;


import hanghae99.rescuepets.chat.dto.ChatRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PetInfoByAPI extends TimeStamped implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String desertionNo;
    @Column
    private String filename;
    @Column
    private String happenDt;
    @Column
    private String happenPlace;
    @Column
    private String kindCd;
    @Column
    private String colorCd;
    @Column
    private String age;
    @Column
    private String weight;
    @Column
    private String noticeNo;
    @Column
    private String noticeSdt;
    @Column
    private String noticeEdt;
    @Column
    private String popfile;
    @Column
    private String processState;
    @Column
    private String sexCd;
    @Column
    private String neuterYn;
    @Column
    private String specialMark;
    @Column
    private String careNm;
    @Column
    private String careTel;
    @Column
    private String careAddr;
    @Column
    private String orgNm;
    @Column
    private String chargeNm;
    @Column
    private String officetel;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private Boolean isExactAddress;

    @Enumerated(value = EnumType.STRING)
    @Column
    private PetStateEnum petStateEnum;

    @OneToMany(mappedBy = "petInfoByAPI", cascade = CascadeType.REMOVE)
    private List<Scrap> scrapList = new ArrayList<>();

    public static PetInfoByAPI of(JSONObject itemObject, PetStateEnum state) {
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
                .petStateEnum(state)
                .build();
    }
    public void update(JSONObject itemObject, PetStateEnum state) {
        this.desertionNo = itemObject.optString(desertionNo);
        this.filename = itemObject.optString(filename);
        this.happenDt = itemObject.optString(happenDt);
        this.happenPlace = itemObject.optString(happenPlace);
        this.kindCd = itemObject.optString(kindCd);
        this.colorCd = itemObject.optString(colorCd);
        this.age = itemObject.optString(age);
        this.weight = itemObject.optString(weight);
        this.noticeNo = itemObject.optString(noticeNo);
        this.noticeSdt = itemObject.optString(noticeSdt);
        this.noticeEdt = itemObject.optString(noticeEdt);
        this.popfile = itemObject.optString(popfile);
        this.processState = itemObject.optString(processState);
        this.sexCd = itemObject.optString(sexCd);
        this.neuterYn = itemObject.optString(neuterYn);
        this.specialMark = itemObject.optString(specialMark);
        this.careNm = itemObject.optString(careNm);
        this.careTel = itemObject.optString(careTel);
        this.careAddr = itemObject.optString(careAddr);
        this.orgNm = itemObject.optString(orgNm);
        this.chargeNm = itemObject.optString(chargeNm);
        this.officetel = itemObject.optString(officetel);
        this.petStateEnum = state;
    }

    public void location(Double latitude, Double longitude, Boolean isExactAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isExactAddress = isExactAddress;
    }
}


//    public void update(PetInfoByAPI petInfo) {
//        this.desertionNo = petInfo.desertionNo;
//        this.filename = petInfo.filename;
//        this.happenDt = petInfo.happenDt;
//        this.happenPlace = petInfo.happenPlace;
//        this.kindCd = petInfo.kindCd;
//        this.colorCd = petInfo.colorCd;
//        this.age = petInfo.age;
//        this.weight = petInfo.weight;
//        this.noticeNo = petInfo.noticeNo;
//        this.noticeSdt = petInfo.noticeSdt;
//        this.noticeEdt = petInfo.noticeEdt;
//        this.popfile = petInfo.popfile;
//        this.processState = petInfo.processState;
//        this.sexCd = petInfo.sexCd;
//        this.neuterYn = petInfo.neuterYn;
//        this.specialMark = petInfo.specialMark;
//        this.careNm = petInfo.careNm;
//        this.careTel = petInfo.careTel;
//        this.careAddr = petInfo.careAddr;
//        this.orgNm = petInfo.orgNm;
//        this.chargeNm = petInfo.chargeNm;
//        this.officetel = petInfo.officetel;
//        this.petStateEnum = petInfo.petStateEnum;
//    }