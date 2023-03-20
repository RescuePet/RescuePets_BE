package hanghae99.rescuepets.member.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.RefreshToken;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<ResponseDto> signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        if (memberRepository.findByNickname(signupRequestDto.getNickname()).isPresent()){
            throw new CustomException(DUPLICATE_NICKNAME);
        }
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Member member = Member.builder()
                .email(signupRequestDto.getEmail())
                .password(password)
                .nickname(signupRequestDto.getNickname())
                .build();

        memberRepository.save(member);
        return ResponseDto.toResponseEntity(SIGN_UP_SUCCESS,new MemberResponseDto(member)
        );

    }
    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        jwtUtil.createToken(response, member);

        return ResponseDto.toResponseEntity(LOGIN_SUCCESS,new MemberResponseDto(member));
    }

    public ResponseEntity<ResponseDto> checkEmail(EmailRequestDto emailRequestDto) {


        // 중복 이메일 확인
        if(memberRepository.findByEmail(emailRequestDto.getEmail()).isPresent()){
            throw new CustomException(DUPLICATE_EMAIL);
        }

        return ResponseDto.toResponseEntity(ACOUNT_CHECK_SUCCESS);
    }

    public ResponseEntity<ResponseDto> checkNickname(NicknameRequestDto nicknameRequestDto) {
        // 중복 닉네임 확인
        if(memberRepository.findByNickname(nicknameRequestDto.getNickname()).isPresent()){
            throw new CustomException(DUPLICATE_EMAIL);
        }

        return ResponseDto.toResponseEntity(EMAIL_CHECK_SUCCESS);
    }


    public ResponseEntity<ResponseDto> Withdrawal(WithDrawalRequestDto withDrawalRequestDto) {
        String email = withDrawalRequestDto.getEmail();
        String password = withDrawalRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        memberRepository.delete(member);
        return ResponseDto.toResponseEntity(WITHDRAWAL_SUCCESS);
    }

    public ResponseEntity<ResponseDto> logout( Member member) {
        RefreshToken refreshToken = refreshTokenRepository.findByMemberEmail(member.getEmail()).orElseThrow(
                () -> new NullPointerException()
        );
        refreshTokenRepository.delete(refreshToken);
        return ResponseDto.toResponseEntity(LOGOUT_SUCCESS);
    }
}
