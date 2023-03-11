package hanghae99.rescuepets.publicpet.controller;

import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import hanghae99.rescuepets.publicpet.service.PublicPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class PublicPetController {
    private final PublicPetService publicPetService;

    @PostMapping("/pets/apisave")
    public String apiSave() throws IOException {
        return publicPetService.apiSave();
    }





    @GetMapping("api/pets/petinfobyapi")
    public PublicPetResponsDto getPublicPet() {
        return publicPetService.getPublicPet();
    }
}