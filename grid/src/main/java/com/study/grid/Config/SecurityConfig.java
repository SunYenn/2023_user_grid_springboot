package com.study.grid.Config;

import com.study.grid.Security.AuthProvider;
import com.study.grid.Security.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        /**
         * cors : 웹 어플리케이션의 도메인이 다른 도메인의 리소스 대해 접근이 허용되는지 체크하는 것.
         * 이때, cross-origin HTTP request 요청 실행.
         */
        configuration.addAllowedOrigin("http://localhost:3000"); //교차 출처 요청에 대한 허용 도메인 설정
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("accesstoken");
        configuration.addExposedHeader("content-disposition");
        configuration.setMaxAge((long) 3600);
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL 패턴의 cors 구성 객체 등록

        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtTokenProvider);

        http
                .httpBasic().disable()
                // csrf : 사이즈간 위조 요청.
                // rest api 서버는 session 기반 인증과 다르게 stateless 하기에 서버에 인증정보를 보관하지 않는다.
                // 그러므로 disabled 해도 됨.
                .csrf().disable()

                // 서버가 클라이언트의 상태를 기억하지 않고 세션을 생성하지 않도록 하는 것.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 커스텀 한 필터를 거쳐 JWT 인증 수행
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests() //요청에 대한 접근 제한을 설정하는 메서드
                // Preflight Request라는 실제 요청 전에 보내지는 CORS 요청에 대해 모두 접근 허용
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/user/*").permitAll() // 회원가입, 로그인, 중복계정 체크는 토큰 없어도 허용
                .anyRequest().hasRole("USER")			  // 이외 나머지는 USER 권한필요
                .and()
                .cors(); // CORS(Cross-Origin Resource Sharing)를 활성화
    }
}