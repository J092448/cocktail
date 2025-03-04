package com.ontherocks.cocktail.config;

import com.ontherocks.cocktail.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)  // 메서드 보안 활성화
@Slf4j
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApplicationEventPublisher eventPublisher) throws Exception {
        http.csrf(csrf -> csrf.disable());  // CSRF 비활성화

        // 페이지 접근 권한 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/findId", "/findPw",
                        "/check-username", "/api/phoneNumber/generateOTP", "/api/phoneNumber/verifyOTP", "/changePassword")
                .permitAll()  // 로그인 및 회원가입 관련 페이지는 인증 없이 접근 가능
                .anyRequest().authenticated()  // 그 외 페이지는 인증 필요
        );

        // 로그인 설정
        http.formLogin(form -> form
                .loginPage("/login")  // 커스텀 로그인 페이지
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    eventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
                    String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
                    if ("ROLE_USER".equals(role)) {
                        response.sendRedirect("/showNotice");  // 일반 사용자 로그인 후 이동
                    } else if ("ROLE_ADMIN".equals(role)) {
                        response.sendRedirect("/admin/main");  // 관리자 로그인 후 이동
                    } else {
                        response.sendRedirect("/dashboard");  // 기본적으로 대시보드로 이동
                    }
                })
                .failureHandler((request, response, exception) -> {
                    Throwable cause = exception.getCause();
                    if (cause instanceof DisabledException) {
                        request.getSession().setAttribute("msg", "정지된 계정입니다. 관리자에게 문의하세요.");
                    } else {
                        request.getSession().setAttribute("msg", "아이디 또는 비밀번호를 확인하세요.");
                    }
                    response.sendRedirect("/login");
                })
                .permitAll()
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        // UserDetailsService 등록
        http.userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화
    }
}
