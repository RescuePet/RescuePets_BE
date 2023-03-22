package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.NeuterEnum;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.SexEnum;
import hanghae99.rescuepets.common.entity.UpkindEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetPostCatchResponseDto {
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
    private Boolean openNickname;
    private Boolean isWished;
    private List<PostImageResponseDto> postImages;
    private boolean isLinked = false;

    public static PetPostCatchResponseDto of(PetPostCatch petPostCatch) {
        return PetPostCatchResponseDto.builder()
                .id(petPostCatch.getId())
                .upkind(petPostCatch.getUpkind())
                .sexCd(petPostCatch.getSexCd())
                .neuterYn(petPostCatch.getNeuterYn())
                .kindCd(petPostCatch.getKindCd())
                .age(petPostCatch.getAge())
                .weight(petPostCatch.getWeight())
                .colorCd(petPostCatch.getColorCd())
                .happenPlace(petPostCatch.getHappenPlace())
                .happenLongitude(petPostCatch.getHappenLongitude())
                .happenLatitude(petPostCatch.getHappenLatitude())
                .happenDt(petPostCatch.getHappenDt())
                .happenHour(petPostCatch.getHappenHour())
                .specialMark(petPostCatch.getSpecialMark())
                .content(petPostCatch.getContent())
                .gratuity(petPostCatch.getGratuity())
                .contact(petPostCatch.getContact())
                .nickname(petPostCatch.getMember().getNickname())
                .createdAt(petPostCatch.getCreatedAt().toString())
                .modifiedAt(petPostCatch.getModifiedAt().toString())
                .postImages(petPostCatch.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .build();
    }
    public void setWished(boolean wished) {
        isWished = wished;
    }
    public void setLinked(boolean isLinked) {
        this.isLinked = isLinked;
    }
}
