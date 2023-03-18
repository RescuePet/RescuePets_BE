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
    private Boolean isWished;

    public static PetPostMissingResponseDto of(PetPostMissing petPostMissing) {
        return PetPostMissingResponseDto.builder()
                .happenPlace(petPostMissing.getHappenPlace())
                .popfile(petPostMissing.getPopfile())
                .kindCd(petPostMissing.getKindCd())
                .specialMark(petPostMissing.getSpecialMark())
                .content(petPostMissing.getContent())
                .nickname(petPostMissing.getMember().getNickname())
                .createdAt(petPostMissing.getCreatedAt().toString())
                .modifiedAt(petPostMissing.getModifiedAt().toString())
                .build();
    }

    public void setWished(boolean wished) {
        isWished = wished;
    }
}
