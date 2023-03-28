package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.NeuterEnum;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.common.entity.SexEnum;
import hanghae99.rescuepets.common.entity.UpkindEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetPostMissingShortResponseDto {
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

    public static PetPostMissingShortResponseDto of(PetPostMissing petPostMissing) {
        return PetPostMissingShortResponseDto.builder()
                .id(petPostMissing.getId())
                .upkind(petPostMissing.getUpkind())
                .sexCd(petPostMissing.getSexCd())
                .neuterYn(petPostMissing.getNeuterYn())
                .kindCd(petPostMissing.getKindCd())
                .age(petPostMissing.getAge())
                .weight(petPostMissing.getWeight())
                .colorCd(petPostMissing.getColorCd())
                .happenPlace(petPostMissing.getHappenPlace())
                .happenDt(petPostMissing.getHappenDt())
                .createdAt(petPostMissing.getCreatedAt().toString())
                .modifiedAt(petPostMissing.getModifiedAt().toString())
                .postImages(petPostMissing.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .build();
    }
    public void setWished(Boolean isWished) {
        this.isWished = isWished;
    }
}
