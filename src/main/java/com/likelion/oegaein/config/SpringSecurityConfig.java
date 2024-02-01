//package com.likelion.oegaein.config;
//
//import com.likelion.oegaein.service.member.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SpringSecurityConfig {
//    @Autowired
//    private MemberService service;
//
//    // 접근 권한 설정
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                        authorizeHttpRequests
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/**").hasRole("USER")
//                )
//                .formLogin(withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        final String password = encoder.encode("password");
//        UserDetails user = User.withUsername("user")
//                .password(password)
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(password)
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//
//
//        // 사이트 위변조 요청 방지
//        http.csrf().disable();
//
//        // 로그인 설정
//        http.formLogin()
//                .loginPage("/user2/login")
//                .defaultSuccessUrl("/user2/loginSuccess")
//                .failureUrl("/user2/login?success=100)")
//                .usernameParameter("uid")
//                .passwordParameter("pass");
//
//        // 로그아웃 설정
//        http.logout()
//                .invalidateHttpSession(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
//                .logoutSuccessUrl("/user2/login?success=200");
//
//        // 사용자 인증 처리 컴포넌트 서비스 등록
//        http.userDetailsService(service);
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder PasswordEncoder () {
//        //return new MessageDigestPasswordEncoder("SHA-256");
//        return new BCryptPasswordEncoder();
//    }
//}