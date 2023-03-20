package hanghae99.rescuepets.member.controller;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static hanghae99.rescuepets.common.dto.ResponseDto.toAllExceptionResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @Operation(summary = "회원가입", description = "자세한 설명")
    @PostMapping("member/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Validated SignupRequestDto signupRequestDto){
        return memberService.signup(signupRequestDto);

    }

    // 이메일 중복 확인
    @Operation(summary = "이메일 중복 확인", description = "자세한 설명")
    @GetMapping("member/email-duplicate")
    public ResponseEntity<ResponseDto> checkEmail(@RequestParam String email){
       return memberService.checkEmail(email);
    }

    // 닉네임 중복 확인
    @Operation(summary = "닉네임 중복 확인", description = "자세한 설명")
    @GetMapping("member/nickName-duplicate")
    public ResponseEntity<ResponseDto> checkNickname(@RequestParam String nickName){
         return memberService.checkNickname(nickName);
    }

    // 로그인 하기
    @Operation(summary = "로그인",description = "자세한 설명")
    @PostMapping("/member/login")
    public  ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @Operation(summary = "로그아웃", description = "자세한 설명")
    @GetMapping("member/logout")
    public ResponseEntity<ResponseDto> logout(@AuthenticationPrincipal MemberDetails memberDetails){
        return memberService.logout(memberDetails.getMember());
    }
    // 삭제 여기는 전역 Response 나오면 처리 하겠습니다 회원탈퇴
    @Operation(summary = "회원탈퇴", description = "자세한 설명")
    @PostMapping("/member/Withdrawal")
        public ResponseEntity<ResponseDto> Withdrawal(@RequestBody WithDrawalRequestDto withDrawalRequestDto) {
            return memberService.Withdrawal(withDrawalRequestDto);
        }
    }


