package hanghae99.rescuepets.publicpet.dto;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class PublicPetsResponsDto {
    List<PublicPetResponsDto> publicPetResponsDto = new ArrayList<>();
    Boolean isLast = false;

    public PublicPetsResponsDto(List<PublicPetResponsDto> publicPetResponsDto, Boolean isLast) {
        this.publicPetResponsDto = publicPetResponsDto;
        this.isLast = isLast;
    }
    public static PublicPetsResponsDto of(List<PublicPetResponsDto> publicPetResponsDto, Boolean isLast) {
        return PublicPetsResponsDto.builder()
                .publicPetResponsDto(publicPetResponsDto)
                .isLast(isLast)
                .build();
    }
}
