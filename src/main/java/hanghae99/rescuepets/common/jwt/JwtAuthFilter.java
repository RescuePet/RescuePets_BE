package hanghae99.rescuepets.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanghae99.rescuepets.common.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.resolveToken(request, "Access");
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");

        if (accessToken != null) {
            if (jwtUtil.validateToken(accessToken) == 1) {
                setAuthentication(jwtUtil.getEmailFromToken(accessToken));
            } else if (jwtUtil.validateToken(accessToken) == 2) {
                jwtExceptionHandler(response, HttpStatus.SEE_OTHER.value());
            } else {
                jwtExceptionHandler(response, HttpStatus.BAD_REQUEST.value());
                return;
            }
        } else if (refreshToken != null) {
            if (!jwtUtil.refreshTokenValidation(refreshToken)) {
                jwtExceptionHandler(response, HttpStatus.BAD_REQUEST.value());
                return;
            }
            setAuthentication(jwtUtil.getEmailFromToken(refreshToken));
        }
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(ErrorResponseDto.of(statusCode, "토큰에러가 발생하였습니다"));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}