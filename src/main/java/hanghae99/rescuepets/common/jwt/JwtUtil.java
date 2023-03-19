package hanghae99.rescuepets.common.jwt;

import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.RefreshToken;
import hanghae99.rescuepets.common.security.MemberDetailServiceImpl;
import hanghae99.rescuepets.member.dto.TokenDto;
import hanghae99.rescuepets.member.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final MemberDetailServiceImpl memberDetailService;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final long ACCESS_TIME = 1000L * 60 * 60 * 24;
    private static final long REFRESH_TIME = 1000L * 60 * 60 * 24 * 7;
    public static final String ACCESS_TOKEN = "Authorization";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    public static final String BEARER_PREFIX = "Bearer ";


    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오는 기능
    public String resolveToken(HttpServletRequest request, String type) {
        String bearerToken = type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public TokenDto createAllToken(String email) {
        return new TokenDto(createToken(email, "Access"), createToken(email, "Refresh"));
    }

    public String createToken(String email, String type) {

        Date date = new Date();

        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return BEARER_PREFIX +
                Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

    }

    // 토큰 검증
    public Integer validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return 1;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            return 0;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            return 2;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            return 0;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            return 0;
        }
    }

    // refreshToken 토큰 검증
    public Boolean refreshTokenValidation(String token) {

        // 1차 토큰 검증
        if (validateToken(token) != 1) {
            return false;
        }

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(getEmailFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken().substring(7));
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String email) {
        UserDetails memberDetails = memberDetailService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }
    // 토큰에서 email 가져오는 기능
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public void createToken(HttpServletResponse response, Member member) {
        TokenDto tokenDto = createAllToken(member.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(member.getEmail());
        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), member.getEmail());
            refreshTokenRepository.save(newToken);
        }
        response.addHeader(ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

}