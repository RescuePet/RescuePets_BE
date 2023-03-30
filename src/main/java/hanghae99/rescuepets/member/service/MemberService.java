package hanghae99.rescuepets.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.RefreshToken;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.common.profanityFilter.ProfanityFiltering;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.member.repository.RefreshTokenRepository;
import hanghae99.rescuepets.memberpet.repository.PetPostCatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
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
    private final PetPostCatchRepository petPostCatchRepository;

    public ResponseEntity<ResponseDto> signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        if (memberRepository.findByNickname(signupRequestDto.getNickname()).isPresent()) {
            throw new CustomException(DUPLICATE_NICKNAME);
        }
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String defaultProfileImage = "https://heukwu.s3.ap-northeast-2.amazonaws.com/images/rescuepet/1f8a1bd3-5762-478f-a389-a800534a0217.png";

        Member member = Member.builder()
                .email(signupRequestDto.getEmail())
                .password(password)
                .nickname(signupRequestDto.getNickname())
                .profileImage(defaultProfileImage)
                .build();

        memberRepository.save(member);
        return ResponseDto.toResponseEntity(SIGN_UP_SUCCESS, new MemberResponseDto(member)
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
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        jwtUtil.createToken(response, member);

        return ResponseDto.toResponseEntity(LOGIN_SUCCESS, new MemberResponseDto(member));
    }

    public ResponseEntity<ResponseDto> checkEmail(EmailRequestDto emailRequestDto) {


        // 중복 이메일 확인
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
    public ResponseEntity<ResponseDto> memberEdit(String nickname, MultipartFile multipartFile, Member member) {
        member = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(UNAUTHORIZED_MEMBER));
        if (multipartFile.getName() != null && multipartFile.getSize() > 0) {
            member.update(s3Uploader.uploadSingle(multipartFile));
        }
        if (nickname != null && !nickname.equalsIgnoreCase("")) {
            if (memberRepository.findByNickname(nickname).isPresent()) {
                throw new CustomException(DUPLICATE_NICKNAME);
            }
            member.updates(nickname);
        }
        MemberReviseResponseDto memberReviseResponseDto = new MemberReviseResponseDto(member);
        return ResponseDto.toResponseEntity(MEMBER_EDIT_SUCCESS, memberReviseResponseDto);
    }

    public ResponseEntity<ResponseDto> getMyPage(Member member) {
        Integer petPostCount = petPostCatchRepository.countByMember(member);
        return ResponseDto.toResponseEntity(TEST_SUCCESS, "petPostCount: " + petPostCount);
    }
}

