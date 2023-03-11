package hanghae99.rescuepets.common.config;


import hanghae99.rescuepets.common.jwt.JwtAuthFilter;
import hanghae99.rescuepets.common.security.JwtAccessDeniedHandler;
import hanghae99.rescuepets.common.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CorsFilter corsFilter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .mvcMatchers("/api-docs")
                .mvcMatchers("/docs/**")
                .mvcMatchers("/version")
                .mvcMatchers("/swagger-ui/**")
                .mvcMatchers("/h2-console/**")
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
                .antMatchers("/api/users/signup", "/api/users/login","/api/users/email/**","/api/users/nickName/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/houses", "/api/houses/{houseId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tagTypes", "/api/tagTypes/byHome").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tags").permitAll()
                .antMatchers("/api/tag/**").permitAll()
                .antMatchers("/api/**").authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                .and()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, CorsFilter.class);



        return http.build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

