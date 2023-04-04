package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostShortResponseDto {
    private Long id;
    private UpkindEnum upkind;
    private SexEnum sexCd;
    private NeuterEnum neuterYn;
    private String kindCd;
    private String age;
    private String weight;
    private String colorCd;
    private String happenPlace;
    private String happenDt;
    private String createdAt;
    private String modifiedAt;
    private PostTypeEnum postType;
    private Boolean isWished;
    private List<PostImageResponseDto> postImages;

    public static PostShortResponseDto of(Post post) {
        return PostShortResponseDto.builder()
                .id(post.getId())
                .upkind(post.getUpkind())
                .sexCd(post.getSexCd())
                .neuterYn(post.getNeuterYn())
                .kindCd(post.getKindCd())
                .age(post.getAge())
                .weight(post.getWeight())
                .colorCd(post.getColorCd())
                .happenPlace(post.getHappenPlace())
                .happenDt(post.getHappenDt())
                .createdAt(post.getCreatedAt().toString())
                .modifiedAt(post.getModifiedAt().toString())
                .postImages(post.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .postType(post.getPostType())
                .build();
    }
    public void setWished(Boolean isWished) {
        this.isWished = isWished;
    }
}
