package com.likelion.oegaein.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.oegaein.domain.member.repository.MemberRepository;
import com.likelion.oegaein.domain.member.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // security 기본 설정
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsFilter()))
                .csrf(AbstractHttpConfigurer::disable)
                //.headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable()) // h2
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // authentication 관련 설정
        http.authorizeHttpRequests((configure) -> {
            // ALL 인증
            configure.requestMatchers("/api/v1/roommate-alarms**").authenticated();
            configure.requestMatchers("/api/v1/delivery-alarms**").authenticated();
            configure.requestMatchers("/api/v1/comments**").authenticated();
            configure.requestMatchers("/api/v1/replies**").authenticated();
            configure.requestMatchers("/api/v1/member/like**").authenticated();
            configure.requestMatchers("/api/v1/member/block**").authenticated();
            configure.requestMatchers("/api/v1/member/profile**").authenticated();
            // GET 인증
            configure.requestMatchers(HttpMethod.GET,
                    "/api/v1/my-matchingposts**",
                    "/api/v1/my-matchingrequests**",
                    "/api/v1/come-matchingrequests**",
                    "/api/v1/member/my-profile**",
                    "/api/v1/review**",
                    "/api/v1/reviews**"
            ).authenticated();
            // POST 인증
            configure.requestMatchers(HttpMethod.POST,
                    "/api/v1/matchingposts**",
                    "/api/v1/matchingrequests**",
                    "/api/v1/member/block**",
                    "/api/v1/review**"
            ).authenticated();
            // PUT 인증
            configure.requestMatchers(HttpMethod.PUT,
                    "/api/v1/matchingposts**"
            ).authenticated();
            // PATCH 인증
            configure.requestMatchers(HttpMethod.PATCH,
                "/api/v1/matchingrequests**",
                    "/api/v1/matchingrequests/*/accept",
                    "/api/v1/matchingrequests/*/reject"
            ).authenticated();
            // DELETE 인증
            configure.requestMatchers(HttpMethod.DELETE,
                    "/api/v1/matchingposts**",
                    "/api/v1/matchingrequests**"
            ).authenticated();
            configure.anyRequest().permitAll();
        });
        http.addFilterBefore(jwtAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationExceptionHandlerFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // cors 설정
        config.setAllowedOriginPatterns(List.of("http://127.0.0.1:3000", "http://localhost:3000"));
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("Set-Cookie");
        config.setAllowCredentials(true);
        // source -> config 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtUtil, memberRepository);
    }

    @Bean
    public JwtAuthenticationExceptionHandlerFilter jwtAuthenticationExceptionHandlerFilter(){
        return new JwtAuthenticationExceptionHandlerFilter(objectMapper);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception{
        return (web) -> web.ignoring()
                .requestMatchers("/error");
    }
}