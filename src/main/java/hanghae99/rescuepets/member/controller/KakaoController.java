package hanghae99.rescuepets.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hanghae99.rescuepets.member.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        log.info("카카오 로그인");
        log.info("code : " + code);

        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        return kakaoService.kakaoLogin(code, response);
    }
}
