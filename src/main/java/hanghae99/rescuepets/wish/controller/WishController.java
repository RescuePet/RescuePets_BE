package hanghae99.rescuepets.wish.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.wish.service.WishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WishController {

    private final WishService wishService;

    @Operation(summary = "발견 게시글 관심 등록")
    @PostMapping("/wish/catch/{catchId}")
    public ResponseEntity<ResponseDto> wishCath(@PathVariable Long catchId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.wishCatch(catchId, memberDetails.getMember());
    }

    @Operation(summary = "실종 게시글 관심 등록")
    @PostMapping("/wish/missing/{missingId}")
    public ResponseEntity<ResponseDto> wishMissing(@PathVariable Long missingId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.wishMissing(missingId, memberDetails.getMember());
    }

    @Operation(summary = "발견 게시글 관심 해제")
    @DeleteMapping("/wish/catch/{catchId}")
    public ResponseEntity<ResponseDto> deleteCatch(@PathVariable Long catchId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.deleteCatch(catchId, memberDetails.getMember());
    }

    @Operation(summary = "실종 게시글 관심 해제")
    @DeleteMapping("/wish/missing/{missingId}")
    public ResponseEntity<ResponseDto> deleteMissing(@PathVariable Long missingId, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.deleteMissing(missingId, memberDetails.getMember());
    }
}
