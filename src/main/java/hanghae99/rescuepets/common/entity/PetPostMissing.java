package hanghae99.rescuepets.common.entity;

//import hanghae99.rescuepets.memberpet.dto.MemberPetRequestDto;
//import hanghae99.rescuepets.memberpet.dto.PetPostMissingRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import hanghae99.rescuepets.memberpet.dto.PetPostMissingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "petPostMissing")
@Getter
@NoArgsConstructor
public class PetPostMissing extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kindCd;
    private String age;
    private String weight;
    private String colorCd;
    private String happenPlace;
    private String happenDt;
    private String happenHour;
    private String specialMark;
    private String content;
    private String gratuity;
    private String contact;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SexEnum sexCd;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private NeuterEnum neuterYn;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UpkindEnum upkind;
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;
    @OneToMany(mappedBy = "petPostMissing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();
    @OneToMany(mappedBy = "petPostMissing", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "petPostMissing", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();

    public PetPostMissing(PetPostMissingRequestDto requestDto, Member member) {
        this.upkind = requestDto.getUpkind();
        this.kindCd = requestDto.getKindCd();
        this.sexCd = requestDto.getSexCd();
        this.neuterYn = requestDto.getNeuterYn();
        this.age = requestDto.getAge();
        this.weight = requestDto.getWeight();
        this.colorCd = requestDto.getColorCd();
        this.happenPlace = requestDto.getHappenPlace();
        this.happenDt = requestDto.getHappenDt();
        this.happenHour = requestDto.getHappenHour();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.gratuity = requestDto.getGratuity();
        this.contact = requestDto.getContact();
        this.member = member;
    }
    public void addPostImage(PostImage postImage) {
        this.postImages.add(postImage);
        if (!postImage.getPetPostMissing().equals(this)) {
            postImage.setPostImage(this);
        }
    }
    public void update(PetPostMissingRequestDto requestDto) {
        this.upkind = requestDto.getUpkind();
        this.kindCd = requestDto.getKindCd();
        this.sexCd = requestDto.getSexCd();
        this.neuterYn = requestDto.getNeuterYn();
        this.age = requestDto.getAge();
        this.weight = requestDto.getWeight();
        this.colorCd = requestDto.getColorCd();
        this.happenPlace = requestDto.getHappenPlace();
        this.happenDt = requestDto.getHappenDt();
        this.happenHour = requestDto.getHappenHour();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.gratuity = requestDto.getGratuity();
        this.contact = requestDto.getContact();
    }

}

