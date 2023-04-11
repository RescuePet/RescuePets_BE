package hanghae99.rescuepets.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.dto.SuccessMessage;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.MemberRoleEnum;
import hanghae99.rescuepets.common.jwt.JwtUtil;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.member.dto.KakaoUserInfoDto;
import hanghae99.rescuepets.member.dto.MemberResponseDto;
import hanghae99.rescuepets.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.admin.key}")
    private String kakaoAdminKey;

    @Transactional
    public ResponseEntity<ResponseDto> kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        String accessToken = getAccessToken(code);
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        Member kakaoMember = registerKakaoUserIfNeeded(kakaoUserInfo);
        forceLogin(kakaoMember);
        jwtUtil.createToken(response, kakaoMember);

        return ResponseDto.toResponseEntity(SuccessMessage.LOGIN_SUCCESS, new MemberResponseDto(kakaoMember));
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoApiKey);
        body.add("redirect_uri", "https://rescuepets.co.kr/kakaologin");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoUserInfoRequest, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String profileImage = jsonNode.get("kakao_account").get("profile").get("profile_image_url").asText().replace("http://", "https://");

        return new KakaoUserInfoDto(id, nickname, email, profileImage);
    }

    private Member registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        Long kakaoId = kakaoUserInfo.getId();
        Member kakaoUser = memberRepository.findByKakaoId(kakaoUserInfo.getId()).orElse(null);

        if (kakaoUser == null) {
            Member sameEmailUser = memberRepository.findByEmail(kakaoUserInfo.getEmail()).orElse(null);

            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                kakaoUser.setKakao(kakaoId, kakaoUserInfo.getProfileImage());
            } else {
                kakaoUser = Member.builder()
                        .nickname(getNickname(kakaoUserInfo))
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .email(kakaoUserInfo.getEmail())
                        .kakaoId(kakaoId)
                        .profileImage(kakaoUserInfo.getProfileImage())
                        .memberRoleEnum(MemberRoleEnum.MEMBER)
                        .build();
            }
            memberRepository.save(kakaoUser);
        }

        return kakaoUser;
    }

    private String getNickname(KakaoUserInfoDto kakaoUserInfo) {
        String nickname = kakaoUserInfo.getNickname();
        int i = memberRepository.countByNickname(nickname);
        return i == 0 ? nickname : nickname + "(" + i + ")";
    }

    private void forceLogin(Member kakaoUser) {
        MemberDetails userDetails = new MemberDetails(kakaoUser, kakaoUser.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void unlink(Member member) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "KakaoAK " + kakaoAdminKey);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target_id_type", "user_id");
        body.add("target_id", String.valueOf(member.getKakaoId())); //Rest API 키

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange("https://kapi.kakao.com/v1/user/unlink", HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(ExceptionMessage.KAKAO_UNLINK_FAIL);
        }
    }
}
