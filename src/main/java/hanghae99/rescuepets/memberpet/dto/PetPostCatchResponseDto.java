package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.PetPostCatch;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class PetPostCatchResponseDto {

    private String happenPlace;
    private String popfile;
    private String kindCd;
    private String specialMark;
    private String content;
    private String nickname;
    private String createdAt;
    private String modifiedAt;
    private Boolean openNickname;
    private Boolean isWished;
    private List<PostImageResponseDto> postImages;

    public static PetPostCatchResponseDto of(PetPostCatch petPostCatch) {
        return PetPostCatchResponseDto.builder()
                .happenPlace(petPostCatch.getHappenPlace())
                .popfile(petPostCatch.getPopfile())
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
