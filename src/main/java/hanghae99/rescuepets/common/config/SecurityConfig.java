package hanghae99.rescuepets.common.config;

import hanghae99.rescuepets.common.jwt.JwtAuthFilter;
import hanghae99.rescuepets.common.security.JwtAccessDeniedHandler;
import hanghae99.rescuepets.common.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
                .mvcMatchers("/api-docs")
                .mvcMatchers("/docs/**")
                .mvcMatchers("/version")
                .mvcMatchers("/swagger-ui/**")
                .mvcMatchers("/public");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()

                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/member/signup", "/api/member/login","/api/users/email-duplicate/**","/api/users/nickName/**").permitAll()
                .antMatchers(HttpMethod.GET, "api/pets/petinfobyapi/**", "api/pets/catch/**","api/pets/missing","api/pets/missing/comments/**").permitAll()
                .antMatchers(HttpMethod.GET, "api/chat/catchroom/**","api/chat/missingroom/**","chat/rooms","chat/rooms/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/pets/info-list/**","/api/pets/details/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/mypage/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/pets/api-compare-data/**","/api/pets/api-new-save/**").permitAll()
                .antMatchers("/api/**").authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOrigin("https://rescuepets.co.kr");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "DELETE", "PUT"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
