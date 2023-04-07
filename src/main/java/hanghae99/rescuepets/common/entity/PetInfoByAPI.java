package hanghae99.rescuepets.common.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Enumerated(value = EnumType.STRING)
    @Column
    private PetStateEnum petStateEnum;

    @OneToMany(mappedBy = "petInfoByAPI", cascade = CascadeType.REMOVE)
    private List<Scrap> scrapList = new ArrayList<>();

    public void update(PetInfoByAPI petInfo) {
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
        this.petStateEnum = petInfo.petStateEnum;
    }

    public void location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
