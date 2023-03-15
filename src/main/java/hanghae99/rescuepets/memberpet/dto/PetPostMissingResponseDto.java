package hanghae99.rescuepets.memberpet.dto;

        import hanghae99.rescuepets.common.entity.PetPostCatch;
        import hanghae99.rescuepets.common.entity.PetPostMissing;
        import lombok.Builder;
        import lombok.Getter;
        import java.util.Date;
@Getter
@Builder
public class PetPostMissingResponseDto {

    private Date postedDate;
    private String happenPlace;
    private String popfile;
    private String kindCd;
    private String specialMark;
    private String content;
    private String nickname;
    private String createdAt;
    private String modifiedAt;

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
                .build();
    }
}
