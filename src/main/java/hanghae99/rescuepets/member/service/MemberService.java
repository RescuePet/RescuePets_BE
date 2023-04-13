package hanghae99.rescuepets.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.*;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.common.profanityFilter.ProfanityFiltering;
import hanghae99.rescuepets.common.s3.S3Uploader;
import hanghae99.rescuepets.member.dto.*;
import hanghae99.rescuepets.member.repository.MemberRepository;
import hanghae99.rescuepets.member.repository.RefreshTokenRepository;

import hanghae99.rescuepets.report.repository.ReportRepository;
import hanghae99.rescuepets.report.service.SuspensionLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.*;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;
import static hanghae99.rescuepets.common.entity.MemberRoleEnum.ADMIN;
import static hanghae99.rescuepets.common.entity.MemberRoleEnum.MANAGER;

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
                () -> new CustomException(ID_PASSWORDS_INCORRECT)
        );
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ID_PASSWORDS_INCORRECT);
        }
        if(member.getStop()){
            if(!SuspensionLogic.shouldSuspend(member.getReportDate())){
                Duration timeLimit = SuspensionLogic.getTimeDifference(member.getReportDate());
                String timeLimits = SuspensionLogic.toKoreanDuration(timeLimit);
                return ResponseDto.toResponseEntity(TIMECHECK_SUCCESS,new TimeResponseDto(timeLimits));
            }
            else{
                member.start();
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

        return ResponseDto.toResponseEntity(MEMBER_EDIT_SUCCESS, new MemberReviseResponseDto(member));
    }

    @Transactional
    public ResponseEntity<ResponseDto> setDefaultImage(Member member) {
        member = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(UNAUTHORIZED_MEMBER));
        member.updateImage(defaultImage);

        return ResponseDto.toResponseEntity(MEMBER_EDIT_SUCCESS);
    }

    @Transactional
    public ResponseEntity<ResponseDto> getMemberList(int page, int size, Member member) {
        List<MemberResponseDto> dtoList = new ArrayList<>();
        if (member.getMemberRoleEnum() == ADMIN || member.getMemberRoleEnum() == MANAGER) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Member> memberList = memberRepository.findByOrderByCreatedAtDesc(pageable);
            for (Member memberTemp : memberList) {
                MemberResponseDto dto = new MemberResponseDto(memberTemp);
                dtoList.add(dto);
            }
        } else {
            throw new CustomException(UNAUTHORIZED_MANAGER);
        }
        return ResponseDto.toResponseEntity(MEMBER_LIST_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> findMember(int page, int size, String keyword, Member member) {
        List<MemberResponseDto> dtoList = new ArrayList<>();
        if (member.getMemberRoleEnum().equals(ADMIN) || member.getMemberRoleEnum().equals(MANAGER)) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Member> memberList = memberRepository.findByNicknameContaining(keyword, pageable);
            for (Member memberTemp : memberList) {
                MemberResponseDto dto = new MemberResponseDto(memberTemp);
                dtoList.add(dto);
            }
        } else {
            throw new CustomException(UNAUTHORIZED_MANAGER);
        }
        return ResponseDto.toResponseEntity(MEMBER_LIST_SUCCESS, dtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> memberRoleChange(MemberRoleRequestDto memberRoleRequestDto, Member member) {
        Optional<Member> memberOptional = memberRepository.findByNickname(memberRoleRequestDto.getNickname());
        if (memberOptional.isEmpty()){
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        if (memberRoleRequestDto.getNickname().equals(member.getNickname())){
            throw new CustomException(SELF_REFERENCE_NOT_ALLOWED);
        }
        if (MemberRoleEnum.valueOf(memberRoleRequestDto.getMemberRoleEnum()) == memberOptional.get().getMemberRoleEnum()){
            throw new CustomException(SAME_ROLE_NOT_ALLOWED);
        }
        if (member.getMemberRoleEnum() == ADMIN) {
            if(memberOptional.get().getMemberRoleEnum().equals(ADMIN)){
                throw new CustomException(NOT_ALLOWED_GRADE);
            }
            memberOptional.get().setMemberRoleEnum(memberRoleRequestDto.getMemberRoleEnum());
        }else if(member.getMemberRoleEnum() == MANAGER) {
            if(memberOptional.get().getMemberRoleEnum().equals(MANAGER)||memberOptional.get().getMemberRoleEnum().equals(ADMIN)){
                throw new CustomException(NOT_ALLOWED_GRADE);
            }else if(memberRoleRequestDto.getMemberRoleEnum().equals("MANAGER")||memberRoleRequestDto.getMemberRoleEnum().equals("ADMIN")){
                throw new CustomException(NOT_ALLOWED_GRADE);
            }
            memberOptional.get().setMemberRoleEnum(memberRoleRequestDto.getMemberRoleEnum());
        }else{
            throw new CustomException(UNAUTHORIZED_MANAGER);
        }
        MemberResponseDto responseDto = new MemberResponseDto(memberOptional.get());
        return ResponseDto.toResponseEntity(MEMBER_EDIT_SUCCESS, responseDto);
    }
}

