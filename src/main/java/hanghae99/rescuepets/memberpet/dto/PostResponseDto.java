package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private UpkindEnum upkind;
    private SexEnum sexCd;
    private NeuterEnum neuterYn;
    private String kindCd;
    private String petName;
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
    private String nickname = "익명";
    private String createdAt;
    private String modifiedAt;
    private Boolean openNickname = true;
    private PostTypeEnum postType;
    private Boolean isWished = false;
    private Integer wishedCount = 0;
    private List<PostImageResponseDto> postImages;
    private Boolean isLinked = false;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .upkind(post.getUpkind())
                .sexCd(post.getSexCd())
                .neuterYn(post.getNeuterYn())
                .kindCd(post.getKindCd())
                .petName(post.getPetName())
                .age(post.getAge())
                .weight(post.getWeight())
                .colorCd(post.getColorCd())
                .happenPlace(post.getHappenPlace())
                .happenLongitude(post.getHappenLongitude())
                .happenLatitude(post.getHappenLatitude())
                .happenDt(post.getHappenDt())
                .happenHour(post.getHappenHour())
                .specialMark(post.getSpecialMark())
                .content(post.getContent())
                .gratuity(post.getGratuity())
                .contact(post.getContact())
                .nickname(post.getMember().getNickname())
                .createdAt(post.getCreatedAt().toString())
                .modifiedAt(post.getModifiedAt().toString())
                .postImages(post.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .postType(post.getPostType())
                .build();
    }
    public void setWished(Boolean isWished) {
        this.isWished = isWished;
    }
    public void setWishedCount(Integer wishedCount) {this.wishedCount = wishedCount;}
    public void setLinked(Boolean isLinked) {
        this.isLinked = isLinked;
    }
    public void setNickname(String nickname) {this.nickname = nickname;}
}
