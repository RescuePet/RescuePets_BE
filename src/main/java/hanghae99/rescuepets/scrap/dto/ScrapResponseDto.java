package hanghae99.rescuepets.scrap.dto;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
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
    private String state;
    private String filename;
    private String kindCd;
    private String sexCd;
    private String author;
    private String happenDt;

    public static ScrapResponseDto of(String postType, Long scrapId, PetInfoByAPI petInfoByAPI) {
        return ScrapResponseDto.builder()
                .postType(postType)
                .scrapId(scrapId)
                .postId((petInfoByAPI.getDesertionNo()))
                .state(petInfoByAPI.getPetStateEnum().getKorean())
                .filename(petInfoByAPI.getFilename())
                .kindCd(petInfoByAPI.getKindCd())
                .sexCd(petInfoByAPI.getSexCd())
                .author(petInfoByAPI.getCareNm())
                .happenDt(petInfoByAPI.getHappenDt())
                .build();
    }
    public static ScrapResponseDto of(Long scrapId, Post post) {
        return ScrapResponseDto.builder()
                .postType(post.getPostType().getPostType())
                .scrapId(scrapId)
                .postId(post.getId())
                .state(changePostTypeToState(post.getPostType().getPostType()))
                .filename(post.getPostImages().stream().map(PostImageResponseDto::of).toList().get(0).getImageURL())
                .kindCd(post.getKindCd())
                .sexCd(post.getSexCd().getSex())
                .author(post.getMember().getNickname())
                .happenDt(post.getHappenDt())
                .build();
    }
    private static String changePostTypeToState(String postType){
        switch (postType) {
            case "catch" -> postType = "목격";
            case "missing" -> postType = "실종";
        }
        return postType;
    }
}
