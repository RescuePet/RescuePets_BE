package hanghae99.rescuepets.publicpet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class PublicPetListResponseDto {
    List<PublicPetResponseDto> publicPetResponseDto = new ArrayList<>();
    Boolean isLast = false;

    public static PublicPetListResponseDto of(List<PublicPetResponseDto> publicPetResponseDto, Boolean isLast) {
        return PublicPetListResponseDto.builder()
                .publicPetResponseDto(publicPetResponseDto)
                .isLast(isLast)
                .build();
    }
}
