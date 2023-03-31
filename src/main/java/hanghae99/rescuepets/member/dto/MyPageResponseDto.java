package hanghae99.rescuepets.member.dto;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponseDto {
    Integer postCount;
    Integer commentCount;
    Integer scrapCount;

    public static MyPageResponseDto of(Integer postCount, Integer commentCount, Integer scrapCount) {
        return MyPageResponseDto.builder()
                .postCount(postCount)
                .commentCount(commentCount)
                .scrapCount(scrapCount)
                .build();
    }
}