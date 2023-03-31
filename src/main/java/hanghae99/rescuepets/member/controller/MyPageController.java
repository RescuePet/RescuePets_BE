package hanghae99.rescuepets.member.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.member.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageService myPageService;


    @Operation(summary = "회원정보")
    @GetMapping
    public ResponseEntity<ResponseDto> getMyPage(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return myPageService.getMyPage(memberDetails.getMember());
    }
}
