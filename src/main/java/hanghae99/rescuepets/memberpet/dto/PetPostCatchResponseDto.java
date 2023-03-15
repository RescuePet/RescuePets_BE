package hanghae99.rescuepets.memberpet.dto;

import hanghae99.rescuepets.common.entity.PetPostCatch;
import lombok.Builder;
import lombok.Getter;
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
                .build();
    }

    public void setWished(boolean wished) {
        isWished = wished;
    }
}
