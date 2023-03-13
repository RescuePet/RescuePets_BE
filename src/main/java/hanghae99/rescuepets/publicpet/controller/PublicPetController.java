package hanghae99.rescuepets.publicpet.controller;

import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.publicpet.dto.PublicPetResponsDto;
import hanghae99.rescuepets.publicpet.service.PublicPetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "유기동물 공공 API")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PublicPetController {
    private final PublicPetService publicPetService;

    @PostMapping("/api-save/{pageNo}")
    @Operation(summary = "공공데이터 유기동물API 호출 및 DB 저장", description = "자세한 설명")
    public String apiSave(@PathVariable(value = "pageNo") String pageNo,
                          @RequestParam(value = "state") String state) throws IOException {
        return publicPetService.apiSave(pageNo, state);
    }


    //    @SecurityRequirements
    @GetMapping("/info-list")
    @Operation(summary = "유기동물 전체 정보 가져오기", description = "자세한 설명")
    public List<PublicPetResponsDto> getPublicPet(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size, @RequestParam(value = "sortBy", required = false, defaultValue = "happenDt") String sortBy, @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.getPublicPet(page - 1, size, sortBy, memberDetails.getMember());
    }
    @GetMapping("/details/{desertionNo}")
    @Operation(summary = "유기동물 상세 정보 가져오기", description = "자세한 설명")
    public PublicPetResponsDto getPublicPetDetails(@PathVariable(value = "desertionNo") String desertionNo, @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.getPublicPetDetails(desertionNo, memberDetails.getMember());
    }

    @PostMapping("/likes")
    @Operation(summary = "유기동물 관심 등록", description = "자세한 설명")
    public String petinfoLike(@PathVariable(value = "desertionNo") String desertionNo) {
        return null;
    }
}