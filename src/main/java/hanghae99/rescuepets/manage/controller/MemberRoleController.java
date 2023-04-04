package hanghae99.rescuepets.manage.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.manage.dto.MemberRequestDto;
import hanghae99.rescuepets.manage.service.MemberRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멤버 등급관리 API")
@RequestMapping("/api/manage")
@RequiredArgsConstructor
@RestController
public class MemberRoleController {
    private final MemberRoleService memberRoleService;
    @PutMapping(value = "/{memberId}")
    @Operation(summary = "Member 등급 변경하기", description = "관리자 또는 매니저 권한으로 대상 회원의 등급을 불량, 일반, 매니저 중에서 하나를 지정해 변경합니다.")
    public ResponseEntity<ResponseDto> changeEnum(@PathVariable Long memberId,
                                                  @ModelAttribute MemberRequestDto requestDto,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberRoleService.changeEnum(memberId, requestDto, memberDetails.getMember());
    }
}
