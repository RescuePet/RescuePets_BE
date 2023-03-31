package hanghae99.rescuepets.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KakaoService kakaoService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseEntity<ResponseDto> signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        if (memberRepository.findByNickname(signupRequestDto.getNickname()).isPresent()){
            throw new CustomException(DUPLICATE_NICKNAME);
        }

        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String defaultProfileImage = "https://s3.ap-northeast-2.amazonaws.com/youngsbucket/images/rescuepet/5f818015-c99e-4b29-afc9-dce98d801d90.png";

        Member member = Member.builder()
                .email(signupRequestDto.getEmail())
                .password(password)
                .nickname(signupRequestDto.getNickname())
                .profileImage(defaultProfileImage)
                .build();
        memberRepository.save(member);

        return ResponseDto.toResponseEntity(SIGN_UP_SUCCESS, new MemberResponseDto(member));
    }

    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        jwtUtil.createToken(response, member);

        return ResponseDto.toResponseEntity(LOGIN_SUCCESS, new MemberResponseDto(member));
    }

    public ResponseEntity<ResponseDto> checkEmail(EmailRequestDto emailRequestDto) {
        if(memberRepository.findByEmail(emailRequestDto.getEmail()).isPresent()){
            throw new CustomException(DUPLICATE_EMAIL);
        }

        return ResponseDto.toResponseEntity(ACOUNT_CHECK_SUCCESS);
    }

    public ResponseEntity<ResponseDto> checkNickname(NicknameRequestDto nicknameRequestDto) {
        if(memberRepository.findByNickname(nicknameRequestDto.getNickname()).isPresent()){
            throw new CustomException(DUPLICATE_EMAIL);
        }

        return ResponseDto.toResponseEntity(EMAIL_CHECK_SUCCESS);
    }

    public ResponseEntity<ResponseDto> logout(Member member) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail((member.getEmail()));
        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteByMemberEmail(member.getEmail());
        }

        return ResponseDto.toResponseEntity(LOGOUT_SUCCESS);
    }

    public ResponseEntity<ResponseDto> Withdrawal(Member member) {
        member = memberRepository.findById(member.getId()).orElseThrow(NullPointerException::new);
        if (member.getKakaoId() != null) {
            try {
                kakaoService.unlink(member);
            } catch (JsonProcessingException e) {
                throw new CustomException(KAKAO_UNLINK_FAIL);
            }
        }
        member.withdrawal();
        return ResponseDto.toResponseEntity(WITHDRAWAL_SUCCESS);
    }
}
