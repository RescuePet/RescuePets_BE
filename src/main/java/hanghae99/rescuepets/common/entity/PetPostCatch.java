package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "petPostCatch")
@Getter
@NoArgsConstructor
public class PetPostCatch extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String upkind;
    private String kindCd;
    private String sexCd;
    private String neuterYn;
    private String age;
    private String weight;
    private String colorCd;
    private String happenPlace;
    private String happenDt;
    private String happenHour;
    private String specialMark;
    private String content;
    private Boolean openNickname;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;
    @OneToMany(mappedBy = "petPostCatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();
    @OneToMany(mappedBy = "petPostCatch", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "petPostCatch", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();


    public PetPostCatch(PetPostCatchRequestDto requestDto, Member member) {
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
        this.member = member;
        this.openNickname = requestDto.getOpenNickname();
    }
    public void addPostImage(PostImage postImage) {
        this.postImages.add(postImage);
        if (!postImage.getPetPostCatch().equals(this)) {
            postImage.setPostImage(this);
        }
    }
    public void update(PetPostCatchRequestDto requestDto) {
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
        this.openNickname = requestDto.getOpenNickname();
    }
}
