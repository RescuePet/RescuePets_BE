package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.NeuterEnum;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.SexEnum;
import hanghae99.rescuepets.common.entity.UpkindEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static hanghae99.rescuepets.common.entity.NeuterEnum.YES;

@Getter
@Builder
public class PetPostCatchShortResponseDto {
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
    private Boolean isWished;
    private List<PostImageResponseDto> postImages;

    public static PetPostCatchShortResponseDto of(PetPostCatch petPostCatch) {
        return PetPostCatchShortResponseDto.builder()
                .id(petPostCatch.getId())
                .upkind(petPostCatch.getUpkind())
                .sexCd(petPostCatch.getSexCd())
                .neuterYn(petPostCatch.getNeuterYn())
                .kindCd(petPostCatch.getKindCd())
                .age(petPostCatch.getAge())
                .weight(petPostCatch.getWeight())
                .colorCd(petPostCatch.getColorCd())
                .happenPlace(petPostCatch.getHappenPlace())
                .happenDt(petPostCatch.getHappenDt())
                .createdAt(petPostCatch.getCreatedAt().toString())
                .modifiedAt(petPostCatch.getModifiedAt().toString())
                .postImages(petPostCatch.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .build();
    }
    public void setWished(Boolean isWished) {
        this.isWished = isWished;
    }
}
