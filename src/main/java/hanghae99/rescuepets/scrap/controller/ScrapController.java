package hanghae99.rescuepets.scrap.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.scrap.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrap")
public class ScrapController {

    private final ScrapService scrapService;

    @Operation(summary = "발견 게시글 관심 등록")
    @PostMapping("/catch/{catchId}")
    public ResponseEntity<ResponseDto> scrapCath(@PathVariable Long catchId,
                                                 @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.scrapCatch(catchId, memberDetails.getMember());
    }

    @Operation(summary = "실종 게시글 관심 등록")
    @PostMapping("/missing/{missingId}")
    public ResponseEntity<ResponseDto> scrapMissing(@PathVariable Long missingId,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.scrapMissing(missingId, memberDetails.getMember());
    }

    @Operation(summary = "발견 게시글 관심 해제")
    @DeleteMapping("/catch/{catchId}")
    public ResponseEntity<ResponseDto> deleteCatch(@PathVariable Long catchId,
                                                   @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.deleteCatch(catchId, memberDetails.getMember());
    }

    @Operation(summary = "실종 게시글 관심 해제")
    @DeleteMapping("/missing/{missingId}")
    public ResponseEntity<ResponseDto> deleteMissing(@PathVariable Long missingId,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.deleteMissing(missingId, memberDetails.getMember());
    }
    @PostMapping("/scrap/{desertionNo}")
    @Operation(summary = "유기동물 관심 등록")
    public ResponseEntity<ResponseDto> petInfoScrap(@PathVariable(value = "desertionNo") String desertionNo,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.scrapPublicPet(desertionNo, memberDetails.getMember());
    }

    @DeleteMapping("/scrap/{desertionNo}")
    @Operation(summary = "유기동물 관심 해제")
    public ResponseEntity<ResponseDto> deletePetInfoScrap(@PathVariable(value = "desertionNo") String desertionNo,
                                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.deleteScrapPublicPet(desertionNo, memberDetails.getMember());
    }
}
