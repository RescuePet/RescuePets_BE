package hanghae99.rescuepets.member.controller;

import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("member/signup")
    public MemberResponseDto signup(@RequestBody @Validated SignupRequestDto signupRequestDto){
        return memberService.signup(signupRequestDto);
    }

    // 이메일 중복 확인
    @GetMapping("member/email-duplicate/{email}")
    public EmailResponseDto checkEmail(@RequestParam String email){
        return memberService.checkEmail(email);
    }

    // 닉네임 중복 확인
    @GetMapping("member/nickName-duplicate/{nickname}")
    public NicknameResponseDto checkNickname(@RequestParam String nickName){
        return  memberService.checkNickname(nickName);
    }

    // 로그인 하기
    @PostMapping("/member/login")
    public MemberResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    // 삭제 여기는 전역 Response 나오면 처리 하겠습니다
    @PostMapping("/member/Withdrawal")
        public void Withdrawal(@RequestBody WithDrawalRequestDto withDrawalRequestDto) {
            memberService.Withdrawal(withDrawalRequestDto);
        }
    }


