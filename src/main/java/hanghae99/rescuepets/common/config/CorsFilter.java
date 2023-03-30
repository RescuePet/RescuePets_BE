package hanghae99.rescuepets.common.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

//        String[] allowedOrigins = {"http://localhost:3000", "https://www.rescuepets.co.kr"};
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        // 요청 메서드를 허용합니다.
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        // 요청 헤더를 허용합니다.
        response.setHeader("Access-Control-Allow-Headers", "*");
        // Access-Control-Expose-Headers 헤더 설정
        response.setHeader("Access-Control-Expose-Headers", "*");

        response.setHeader("Access-Control-Allow-Credentials", "true");

        // preflight 요청인 경우, 응답을 보내고 체인을 중지합니다.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
