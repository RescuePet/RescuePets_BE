package hanghae99.rescuepets.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.MemberRoleEnum;
import hanghae99.rescuepets.common.entity.RefreshToken;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.common.profanityFilter.ProfanityFiltering;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.member.repository.RefreshTokenRepository;

import hanghae99.rescuepets.report.repository.ReportRepository;
import hanghae99.rescuepets.report.service.SuspensionLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Optional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ProfanityFiltering profanityFiltering;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KakaoService kakaoService;
    private final S3Uploader s3Uploader;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ReportRepository reportRepository;
    private final String defaultImage = "https://heukwu.s3.ap-northeast-2.amazonaws.com/images/rescuepet/Component+43.png";

    public ResponseEntity<ResponseDto> signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }
        if (memberRepository.findByNickname(signupRequestDto.getNickname()).isPresent()) {
            throw new CustomException(DUPLICATE_NICKNAME);
        }
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        Member member = Member.builder()
                .email(signupRequestDto.getEmail())
                .password(password)
                .nickname(signupRequestDto.getNickname())
                .profileImage(defaultImage)
                .memberRoleEnum(MemberRoleEnum.MEMBER)
                .build();
        memberRepository.save(member);

        return ResponseDto.toResponseEntity(SIGN_UP_SUCCESS, new MemberResponseDto(member));
    }
    @Transactional
    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        if(member.getStop()){
            if(!SuspensionLogic.shouldSuspend(member.getReportDate())){
                Duration timeLimit = SuspensionLogic.getTimeDifference(member.getReportDate());
                String timeLimits = SuspensionLogic.toKoreanDuration(timeLimit);
                return ResponseDto.toResponseEntity(TIMECHECK_SUCCESS,new TimeResponseDto(timeLimits));
            }
            else{
                member.Start();
            }
        }
        jwtUtil.createToken(response, member);

        return ResponseDto.toResponseEntity(LOGIN_SUCCESS, new MemberResponseDto(member));
    }

    public ResponseEntity<ResponseDto> checkEmail(EmailRequestDto emailRequestDto) {
        if (memberRepository.findByEmail(emailRequestDto.getEmail()).isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        return ResponseDto.toResponseEntity(ACCOUNT_CHECK_SUCCESS);
    }

    public ResponseEntity<ResponseDto> checkNickname(NicknameRequestDto nicknameRequestDto) {
        // 비속어 필터링
        if (profanityFiltering.check(nicknameRequestDto.getNickname())) {
            throw new CustomException(PROFANITY_CHECK);
        }
        // 중복 닉네임 확인
        if (memberRepository.findByNickname(nicknameRequestDto.getNickname()).isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        return ResponseDto.toResponseEntity(EMAIL_CHECK_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> logout(Member member) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail((member.getEmail()));
        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteByMemberEmail(member.getEmail());
        }

        return ResponseDto.toResponseEntity(LOGOUT_SUCCESS);
    }

    @Transactional
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

    @Transactional
    public ResponseEntity<ResponseDto> memberEdit(UpdateRequestDto updateRequestDto, Member member) {
        member = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(UNAUTHORIZED_MEMBER));
        if (updateRequestDto.getImage() != null) {
            member.updateImage(s3Uploader.uploadSingle(updateRequestDto.getImage()));
        }
        if (updateRequestDto.getNickname() != null && !updateRequestDto.getNickname().equalsIgnoreCase("")) {
            if (memberRepository.findByNickname(updateRequestDto.getNickname()).isPresent()) {
                throw new CustomException(DUPLICATE_NICKNAME);
            }
            member.updateNickname(updateRequestDto.getNickname());
        }
        MemberReviseResponseDto memberReviseResponseDto = new MemberReviseResponseDto(member);

        return ResponseDto.toResponseEntity(MEMBER_EDIT_SUCCESS, memberReviseResponseDto);
    }

    @Transactional
    public ResponseEntity<ResponseDto> setDefault(Member member) {
        member = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(UNAUTHORIZED_MEMBER));
        member.updateImage(defaultImage);

        return ResponseDto.toResponseEntity(MEMBER_EDIT_SUCCESS);
    }
}

