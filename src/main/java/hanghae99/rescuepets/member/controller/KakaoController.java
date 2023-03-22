package hanghae99.rescuepets.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.member.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class KakaoController {
    private final KakaoService kakaoService;

    @Operation(summary = "카카오 콜백함수")
    @GetMapping("/kakao/callback")
    public ResponseEntity<ResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        log.info("카카오 로그인");
        log.info("code : " + code);

        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        return kakaoService.kakaoLogin(code, response);
    }
}
