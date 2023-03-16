package hanghae99.rescuepets.member.service;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.MemberRoleEnum;
import hanghae99.rescuepets.common.entity.RefreshToken;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    public MemberResponseDto signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByEmailAndNickname(signupRequestDto.getEmail(), signupRequestDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("중복입니다 한번더 확인 해 주세여 ~!");
        }
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        MemberRoleEnum memberRoleEnum = MemberRoleEnum.MEMBER;
        if (signupRequestDto.getIsAdmin() && Objects.equals(ADMIN_TOKEN, signupRequestDto.getAdminToken())) {
            memberRoleEnum = MemberRoleEnum.ADMIN;
        }
        Member member = Member.builder()
                .email(signupRequestDto.getEmail())
                .password(password)
                .nickname(signupRequestDto.getNickname()).memberRoleEnum(memberRoleEnum).build();
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }
    public MemberResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginRequestDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(loginRequestDto.getEmail());
        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return new MemberResponseDto(member);
    }

    public EmailResponseDto checkEmail(String email) {


        // 중복 이메일 확인
        if(memberRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("이메일이 중복 됩니다");
        }

        return new EmailResponseDto(email);
    }

    public NicknameResponseDto checkNickname(String nickName) {
        // 중복 닉네임 확인
        if(memberRepository.findByNickname(nickName).isPresent()){
            throw new IllegalArgumentException("이메일이 중복 됩니다");
        }

        return new NicknameResponseDto(nickName);
    }


    public void Withdrawal(WithDrawalRequestDto withDrawalRequestDto) {
        String email = withDrawalRequestDto.getEmail();
        String password = withDrawalRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        memberRepository.delete(member);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
