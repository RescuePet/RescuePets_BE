package hanghae99.rescuepets.common.entity;

import hanghae99.rescuepets.memberpet.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//import hanghae99.rescuepets.memberpet.dto.PetPostCatchRequestDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "post")
@Getter
@NoArgsConstructor
public class Post extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kindCd;
    private String age;
    private String weight;
    private String colorCd;
    private String happenPlace;
    private String happenLongitude;
    private String happenLatitude;
    private String happenDt;
    private String happenHour;
    private String specialMark;
    private String content;
    private String gratuity;
    private String contact;
    private Boolean openNickname;
    private Boolean isDeleted = false;
    private Date deletedDt;
    private String missingPosterImageURL = "#";
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PostTypeEnum postType;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UpkindEnum upkind;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SexEnum sexCd;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private NeuterEnum neuterYn;
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLink> postLinkList = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Scrap> scrapList = new ArrayList<>();


    public Post(PostRequestDto requestDto, Member member) {
        this.postType = PostTypeEnum.valueOf(requestDto.getPostType());
        this.upkind = requestDto.getUpkind();
        this.sexCd = requestDto.getSexCd();
        this.neuterYn = requestDto.getNeuterYn();
        this.kindCd = requestDto.getKindCd();
        this.age = requestDto.getAge();
        this.weight = requestDto.getWeight();
        this.colorCd = requestDto.getColorCd();
        this.happenPlace = requestDto.getHappenPlace();
        this.happenLongitude = requestDto.getHappenLongitude();
        this.happenLatitude = requestDto.getHappenLatitude();
        this.happenDt = requestDto.getHappenDt();
        this.happenHour = requestDto.getHappenHour();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.gratuity = requestDto.getGratuity();
        this.contact = requestDto.getContact();
        this.member = member;
        this.openNickname = requestDto.getOpenNickname();
    }
    public void addPostImage(PostImage postImage) {
        this.postImages.add(postImage);
        if (!postImage.getPost().equals(this)) {
            postImage.setPostImage(this);
        }
    }
    public void update(PostRequestDto requestDto) {
        this.postType = PostTypeEnum.valueOf(requestDto.getPostType());
        this.upkind = requestDto.getUpkind();
        this.sexCd = requestDto.getSexCd();
        this.neuterYn = requestDto.getNeuterYn();
        this.kindCd = requestDto.getKindCd();
        this.age = requestDto.getAge();
        this.weight = requestDto.getWeight();
        this.colorCd = requestDto.getColorCd();
        this.happenPlace = requestDto.getHappenPlace();
        this.happenLongitude = requestDto.getHappenLongitude();
        this.happenLatitude = requestDto.getHappenLatitude();
        this.happenDt = requestDto.getHappenDt();
        this.happenHour = requestDto.getHappenHour();
        this.specialMark = requestDto.getSpecialMark();
        this.content = requestDto.getContent();
        this.gratuity = requestDto.getGratuity();
        this.contact = requestDto.getContact();
        this.openNickname = requestDto.getOpenNickname();
    }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
    public Boolean getIsDeleted() {
        if(isDeleted == null){
            return false;
        }
        return isDeleted;
    }
    public void setMissingPosterImageURL(String imageURL){
        this.missingPosterImageURL = imageURL;
    }
}
