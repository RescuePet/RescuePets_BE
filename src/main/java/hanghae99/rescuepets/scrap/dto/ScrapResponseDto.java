package hanghae99.rescuepets.scrap.dto;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.PetPostCatch;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import hanghae99.rescuepets.memberpet.dto.PostImageResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ScrapResponseDto {
    private String postType;
    private Long scrapId;
    private Long postId;
    private String desertionNo;
    private String state;
    private String filename; //이미지
    private String kindCd;//종류
    private String sexCd;
    private String author; //보호소 이름 또는 작성자
    private String happenDt; //발생 일자
    private List<PostImageResponseDto> postImages;//사진

    public static ScrapResponseDto of(String postType, Long scrapId, PetInfoByAPI petInfoByAPI) {
        return ScrapResponseDto.builder()
                .postType(postType)
                .scrapId(scrapId)
                .desertionNo(petInfoByAPI.getDesertionNo())
                .state(petInfoByAPI.getPetStateEnum().getKorean())
                .filename(petInfoByAPI.getFilename())
                .kindCd(petInfoByAPI.getKindCd())
                .sexCd(petInfoByAPI.getSexCd())
                .author(petInfoByAPI.getCareNm())
                .happenDt(petInfoByAPI.getHappenDt())
                .build();
    }
    public static ScrapResponseDto of(String postType, Long scrapId, PetPostCatch petPostCatch) {
        return ScrapResponseDto.builder()
                .postType(postType)
                .scrapId(scrapId)
                .postId(petPostCatch.getId())
                .state("목격")
                .postImages(petPostCatch.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .kindCd(petPostCatch.getKindCd())
                .sexCd(petPostCatch.getSexCd().getSex())
                .author(petPostCatch.getMember().getNickname())
                .happenDt(petPostCatch.getHappenDt())
                .build();
    }
    public static ScrapResponseDto of(String postType, Long scrapId, PetPostMissing petPostMissing) {
        return ScrapResponseDto.builder()
                .postType(postType)
                .scrapId(scrapId)
                .postId(petPostMissing.getId())
                .state("실종")
                .postImages(petPostMissing.getPostImages().stream().map(PostImageResponseDto::of).toList())
                .kindCd(petPostMissing.getKindCd())
                .sexCd(petPostMissing.getSexCd().getSex())
                .author(petPostMissing.getMember().getNickname())
                .happenDt(petPostMissing.getHappenDt())
                .build();
    }
}
