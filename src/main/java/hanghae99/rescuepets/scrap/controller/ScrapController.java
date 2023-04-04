package hanghae99.rescuepets.scrap.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.scrap.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "스크랩 API")
@RequestMapping("/api/scrap")
public class ScrapController {

    private final ScrapService scrapService;
    @GetMapping("/list")
    @Operation(summary = "나의 스크랩 전체 가져오기")
    public ResponseEntity<ResponseDto> getPublicPet(@RequestParam(value = "page") int page,
                                                    @RequestParam(value = "size") int size,
                                                    @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.getMyScrapList(page - 1, size, sortBy, memberDetails.getMember());
    }

    @Operation(summary = "게시글 스크랩 등록")
    @PostMapping("/{postId}")
    public ResponseEntity<ResponseDto> scrapPost(@PathVariable Long postId,
                                                 @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.scrapPost(postId, memberDetails.getMember());
    }

    @PostMapping("/petinfo/{desertionNo}")
    @Operation(summary = "유기동물 스크랩 등록")
    public ResponseEntity<ResponseDto> scrapPetInfo(@PathVariable(value = "desertionNo") String desertionNo,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.scrapPetInfo(desertionNo, memberDetails.getMember());
    }

    @Operation(summary = "게시글 스크랩 해제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto> deletePostScrap(@PathVariable Long postId,
                                                       @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.deletePostScrap(postId, memberDetails.getMember());
    }

    @DeleteMapping("/petinfo/{desertionNo}")
    @Operation(summary = "유기동물 관심 해제")
    public ResponseEntity<ResponseDto> deletePetInfoScrap(@PathVariable(value = "desertionNo") String desertionNo,
                                                          @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return scrapService.deleteScrapPublicPet(desertionNo, memberDetails.getMember());
    }
}
