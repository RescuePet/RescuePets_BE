package hanghae99.rescuepets.wish.controller;

import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WishController {

    private final WishService wishService;

    @PostMapping("/wish/{catchId}")
    public String wishCath(@PathVariable Long catchId, @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.wishCatch(catchId, memberDetails.getMember());
    }

    @PostMapping("/wish/{missingId}")
    public String wishMissing(@PathVariable Long missingId, @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.wishMissing(missingId, memberDetails.getMember());
    }

    @DeleteMapping("/wish/{catchId}")
    public String deleteCatch(@PathVariable Long catchId, @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.deleteCatch(catchId, memberDetails.getMember());
    }

    @DeleteMapping("/wish/{missingId}")
    public String deleteMissing(@PathVariable Long missingId, @AuthenticationPrincipal MemberDetails memberDetails) {
        return wishService.deleteMissing(missingId, memberDetails.getMember());
    }
}
