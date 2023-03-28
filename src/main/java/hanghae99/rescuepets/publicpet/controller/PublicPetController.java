package hanghae99.rescuepets.publicpet.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.publicpet.service.PublicPetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "유기동물 공공 API")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PublicPetController {
    private final PublicPetService publicPetService;

    @SecurityRequirements
    @GetMapping("/info-list")
    @Operation(summary = "유기동물 전체 정보 가져오기", description = "자세한 설명")
    public ResponseEntity<ResponseDto> getPublicPet(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size, @RequestParam(value = "sortBy", required = false, defaultValue = "happenDt") String sortBy, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.getPublicPet(page - 1, size, sortBy, memberDetails.getMember());
    }

    @SecurityRequirements
    @GetMapping("/details/{desertionNo}")
    @Operation(summary = "유기동물 상세 정보 가져오기", description = "자세한 설명")
    public ResponseEntity<ResponseDto> getPublicPetDetails(@PathVariable(value = "desertionNo") String desertionNo, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.getPublicPetDetails(desertionNo, memberDetails.getMember());
    }

    @PostMapping("/scrap/{desertionNo}")
    @Operation(summary = "유기동물 관심 등록", description = "자세한 설명")
    public ResponseEntity<ResponseDto> petInfoScrap(@PathVariable(value = "desertionNo") String desertionNo, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.petInfoScrap(desertionNo, memberDetails.getMember());
    }

    @DeleteMapping("/scrap/{PetInfoLikeId}")
    @Operation(summary = "유기동물 관심 삭제", description = "자세한 설명")
    public ResponseEntity<ResponseDto> deletePetInfoScrap(@PathVariable(value = "PetInfoLikeId") Long PetInfoLikeId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.deletePetInfoScrap(PetInfoLikeId, memberDetails.getMember());
    }
    @PostMapping("/inquiry/{desertionNo}")
    @Operation(summary = "유기동물 문의내역 기록", description = "자세한 설명")
    public ResponseEntity<ResponseDto> petInfoInquiryCheck(@PathVariable(value = "desertionNo") String desertionNo, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return publicPetService.petInfoInquiryCheck(desertionNo, memberDetails.getMember());
    }

}