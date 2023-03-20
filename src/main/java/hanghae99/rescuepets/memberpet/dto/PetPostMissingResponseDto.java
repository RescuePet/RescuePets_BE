package hanghae99.rescuepets.memberpet.dto;

        import hanghae99.rescuepets.common.entity.*;
        import lombok.Builder;
        import lombok.Getter;
        import java.util.Date;
        import java.util.List;

@Getter
@Builder
public class PetPostMissingResponseDto {

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
    private Boolean isWished;
    private List<PostImageResponseDto> postImages;

    public static PetPostMissingResponseDto of(PetPostMissing petPostMissing) {
        return PetPostMissingResponseDto.builder()
                .upkind(petPostMissing.getUpkind())
                .kindCd(petPostMissing.getKindCd())
                .sexCd(petPostMissing.getSexCd())
                .neuterYn(petPostMissing.getNeuterYn())
                .age(petPostMissing.getAge())
                .weight(petPostMissing.getWeight())
                .colorCd(petPostMissing.getColorCd())
                .happenPlace(petPostMissing.getHappenPlace())
                .happenDt(petPostMissing.getHappenDt())
                .happenHour(petPostMissing.getHappenHour())
                .specialMark(petPostMissing.getSpecialMark())
                .content(petPostMissing.getContent())
                .nickname(petPostMissing.getMember().getNickname())
                .createdAt(petPostMissing.getCreatedAt().toString())
                .modifiedAt(petPostMissing.getModifiedAt().toString())
                .postImages(petPostMissing.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .build();
    }

    public void setWished(boolean wished) {
        isWished = wished;
    }
}
