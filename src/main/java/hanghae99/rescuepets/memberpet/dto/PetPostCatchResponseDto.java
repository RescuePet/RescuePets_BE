package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.NeuterEnum;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.SexEnum;
import hanghae99.rescuepets.common.entity.UpkindEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

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

    public static PetPostCatchResponseDto of(PetPostCatch petPostCatch) {
        return PetPostCatchResponseDto.builder()
                .happenPlace(petPostCatch.getHappenPlace())
                .kindCd(petPostCatch.getKindCd())
                .specialMark(petPostCatch.getSpecialMark())
                .content(petPostCatch.getContent())
                .nickname(petPostCatch.getMember().getNickname())
                .createdAt(petPostCatch.getCreatedAt().toString())
                .modifiedAt(petPostCatch.getModifiedAt().toString())
                .openNickname(petPostCatch.getOpenNickname())
                .postImages(petPostCatch.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .build();
    }

    public void setWished(boolean wished) {
        isWished = wished;
    }
}
