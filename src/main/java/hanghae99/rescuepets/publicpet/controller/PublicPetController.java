package hanghae99.rescuepets.publicpet.controller;

import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import hanghae99.rescuepets.publicpet.service.PublicPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicPetController {
    private final PublicPetService publicPetService;

    @PostMapping("/pets/apisave/{pageNo}")
    public String apiSave(@PathVariable("pageNo") String pageNo,
                          @RequestParam("state") String state) throws IOException {
        return publicPetService.apiSave(pageNo, state);
    }



//    @SecurityRequirements
//    @ApiOperation(value = "유기 동물 DB List 호출", notes =)
    @GetMapping("/pets/petinfobyapi")
    public List<PublicPetResponsDto> getPublicPet() {

        return publicPetService.getPublicPet();
    }
}