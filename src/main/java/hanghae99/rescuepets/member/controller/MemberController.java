package hanghae99.rescuepets.member.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    //회원가입
    @Operation(summary = "회원가입", description = "자세한 설명")
    @PostMapping("member/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Validated SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);

    }

    // 이메일 중복 확인
    @Operation(summary = "이메일 중복 확인", description = "자세한 설명")
    @GetMapping("member/email-duplicate")
    public ResponseEntity<ResponseDto> checkEmail(@RequestBody EmailRequestDto emailRequestDto) {
        return memberService.checkEmail(emailRequestDto);
    }

    // 닉네임 중복 확인
    @Operation(summary = "닉네임 중복 확인", description = "자세한 설명")
    @PostMapping("member/nickName-duplicate")
    public ResponseEntity<ResponseDto> checkNickname(@RequestBody NicknameRequestDto nicknameRequestDto) {
        return memberService.checkNickname(nicknameRequestDto);
    }

    // 로그인 하기
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
    @DeleteMapping("member/logout")
    public ResponseEntity<ResponseDto> logout(@Parameter(hidden = true)@AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.logout(memberDetails.getMember());
    }

    @Operation(summary = "회원탈퇴")
    @PostMapping("/member/Withdrawal")
    public ResponseEntity<ResponseDto> Withdrawal(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        return memberService.Withdrawal(memberDetails.getMember());
    }
    @Operation(summary = "회원정보수정")
    @PutMapping(value = "/member/edit",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> memberEdit(
                                                  @RequestPart(name="nickname",required = false)  UpdateRequestDto updateRequestDto, @RequestPart(name="image",required = false) MultipartFile multipartFile,@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails){
        return  memberService.memberEdit(updateRequestDto,multipartFile,memberDetails.getMember());
    }
}