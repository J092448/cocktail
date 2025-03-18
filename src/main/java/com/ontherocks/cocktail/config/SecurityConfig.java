package com.ontherocks.cocktail.config;

import com.ontherocks.cocktail.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailsService userDetailsService; // CustomUserDetailsService를 주입받음

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApplicationEventPublisher eventPublisher) throws Exception {
        logger.info("🔒 Security Config Loaded!");

        // CSRF 보호 비활성화
        http.csrf(csrf -> csrf.disable());

        // 접근 제어 설정
        http.authorizeHttpRequests(auth -> {
                    // 고객 관련 페이지는 로그인 없이 접근 가능
                    logger.info("🔓 Public Access: /csPages/**");
                    auth.requestMatchers("/", "/login", "/register", "/css/**", "/js/**",
                                    "/findId", "/findPw", "/check-username",
                                    "/api/phoneNumber/generateOTP", "/api/phoneNumber/verifyOTP", "/changePassword")
                            .permitAll();  // 로그인 및 회원가입 페이지 허용

                    // 메뉴 관련 페이지도 허용
                    auth.requestMatchers("/images/**", "/cocktail/templates/**",
                                    "/menuOrder.html", "/menuDetail.html", "/menuList.html", "/cart.html", "/csPages/**")
                            .permitAll();  // 메뉴 관련 페이지 허용

                    // 나머지 요청은 인증 필요
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .successHandler((request, response, authentication) -> {
                            // 로그인 성공 시 이벤트 발생
                            eventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));

                            // 권한 가져오기
                            String role = authentication.getAuthorities().stream()
                                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                                    .findFirst()
                                    .orElse("ROLE_USER"); // 기본값 설정 (사용자 권한이 없을 경우)

                            // 역할에 따라 리다이렉트
                            if ("ROLE_USER".equals(role)) {
                                response.sendRedirect("/dashboard");
                            } else if ("ROLE_ADMIN".equals(role)) {
                                response.sendRedirect("/admin/main");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            // 로그인 실패 처리
                            Throwable cause = exception.getCause();
                            if (cause instanceof DisabledException) {
                                response.sendRedirect("/login?suspended"); // 정지된 계정으로 로그인 시
                            } else {
                                response.sendRedirect("/login?error=true"); // 로그인 실패 시
                            }
                        })
                        .permitAll()
                );

        // 로그아웃 설정
        http.logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(userDetailsService); // UserDetailsService 등록

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 PasswordEncoder
    }
}
