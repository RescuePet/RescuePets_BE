package hanghae99.rescuepets.publicpet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PublicPetsResponsDto {
    List<PublicPetResponsDto> publicPetResponsDto = new ArrayList<>();
    Boolean isLast = false;

    public PublicPetsResponsDto(List<PublicPetResponsDto> publicPetResponsDto, Boolean isLast) {
        this.publicPetResponsDto = publicPetResponsDto;
        this.isLast = isLast;
    }
}
