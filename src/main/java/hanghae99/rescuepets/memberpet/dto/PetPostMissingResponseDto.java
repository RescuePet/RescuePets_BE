package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.*;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PetPostMissingResponseDto {

    private Long id;
    private UpkindEnum upkind;
    private SexEnum sexCd;
    private NeuterEnum neuterYn;
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
    private String nickname;
    private String createdAt;
    private String modifiedAt;
    private Boolean isWished = false;
    private Integer wishedCount = 0;
    private List<PostImageResponseDto> postImages;
    private Boolean isLinked = false;

    public static PetPostMissingResponseDto of(PetPostMissing petPostMissing) {
        return PetPostMissingResponseDto.builder()
                .id(petPostMissing.getId())
                .upkind(petPostMissing.getUpkind())
                .sexCd(petPostMissing.getSexCd())
                .neuterYn(petPostMissing.getNeuterYn())
                .kindCd(petPostMissing.getKindCd())
                .age(petPostMissing.getAge())
                .weight(petPostMissing.getWeight())
                .colorCd(petPostMissing.getColorCd())
                .happenPlace(petPostMissing.getHappenPlace())
                .happenLongitude(petPostMissing.getHappenLongitude())
                .happenLatitude(petPostMissing.getHappenLatitude())
                .happenDt(petPostMissing.getHappenDt())
                .happenHour(petPostMissing.getHappenHour())
                .specialMark(petPostMissing.getSpecialMark())
                .content(petPostMissing.getContent())
                .gratuity(petPostMissing.getGratuity())
                .contact(petPostMissing.getContact())
                .nickname(petPostMissing.getMember().getNickname())
                .createdAt(petPostMissing.getCreatedAt().toString())
                .modifiedAt(petPostMissing.getModifiedAt().toString())
                .postImages(petPostMissing.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .build();
    }
    public void setWished(Boolean isWished) {
        this.isWished = isWished;
    }
    public void setWishedCount(Integer wishedCount) {this.wishedCount = wishedCount;}
    public void setLinked(Boolean isLinked) {
        this.isLinked = isLinked;
    }
}
