package hanghae99.rescuepets.member.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.service.MemberService;
import hanghae99.rescuepets.memberpet.dto.PostLinkRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "회원가입", description = "자세한 설명")
    @PostMapping("/member/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Validated SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);

    }

    @Operation(summary = "이메일 중복 확인", description = "자세한 설명")
    @PostMapping("/member/email-duplicate")
    public ResponseEntity<ResponseDto> checkEmail(@RequestBody EmailRequestDto emailRequestDto) {
        return memberService.checkEmail(emailRequestDto);
    }

    @Operation(summary = "닉네임 욕설 포함여부 및 중복 확인", description = "자세한 설명")
    @PostMapping("/member/nickName-duplicate")
    public ResponseEntity<ResponseDto> checkNickname(@RequestBody NicknameRequestDto nicknameRequestDto) {
        return memberService.checkNickname(nicknameRequestDto);
    }

    @Operation(summary = "로그인", description = "자세한 설명")
    @PostMapping("/member/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @Operation(summary = "Access Token 재발급")
    @GetMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails, HttpServletResponse response) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(memberDetails.getMember().getEmail(), "Access"));
        return ResponseDto.toResponseEntity(SuccessMessage.REISSUE_ACCESS_TOKEN);
    }

    @Operation(summary = "로그아웃")
    @DeleteMapping("/member/logout")
    public ResponseEntity<ResponseDto> logout(@Parameter(hidden = true)@AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.logout(memberDetails.getMember());
    }

    @Operation(summary = "회원탈퇴")
    @PostMapping("/member/Withdrawal")
    public ResponseEntity<ResponseDto> Withdrawal(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.Withdrawal(memberDetails.getMember());
    }

    @Operation(summary = "회원정보수정")
    @PutMapping(value = "/member/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> memberEdit(@ModelAttribute UpdateRequestDto updateRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.memberEdit(updateRequestDto, memberDetails.getMember());
    }

    @Operation(summary = "프로필 이미지 기본값 변경")
    @PutMapping(value = "/member/default-image")
    public ResponseEntity<ResponseDto> setDefault(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.setDefault(memberDetails.getMember());
    }

    @Operation(summary = "회원목록보기")
    @GetMapping(value = "/member/list")
    public ResponseEntity<ResponseDto> getMemberList(@RequestParam int page,
                                                     @RequestParam int size,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.getMemberList(page-1, size, memberDetails.getMember());
    }
    @Operation(summary = "회원닉네임으로 검색하기", description = "원하는 키워드로 검색하면 해당 키워드를 닉네임에 포함하는 모든 회원을 조회해 줍니다. 예를들어 하늘을 검색하면 파란하늘, 검은하늘, 붉은하늘을 모두 보여줍니다.")
    @GetMapping(value = "/member/list/{memberId}")
    public ResponseEntity<ResponseDto> findMember(@RequestParam int page,
                                                  @RequestParam int size,
                                                  @RequestBody MemberFindRequestDto requestDto,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.findMember(page-1, size, requestDto.getKeyword(), memberDetails.getMember());
    }
    @Operation(summary = "회원등급수정")
    @PutMapping(value = "/member/role")
    public ResponseEntity<ResponseDto> memberRoleChange(@ModelAttribute MemberRoleRequestDto memberRoleRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.memberRoleChange(memberRoleRequestDto, memberDetails.getMember());
    }
}