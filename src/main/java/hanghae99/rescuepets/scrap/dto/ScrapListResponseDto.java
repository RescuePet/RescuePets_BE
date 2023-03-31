package hanghae99.rescuepets.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ScrapListResponseDto {
    List<ScrapResponseDto> scrapResponseDtoList = new ArrayList<>();
    Boolean isLast = false;

    public static ScrapListResponseDto of(List<ScrapResponseDto> scrapResponseDtoList, Boolean isLast) {
        return ScrapListResponseDto.builder()
                .scrapResponseDtoList(scrapResponseDtoList)
                .isLast(isLast)
                .build();
    }
}
