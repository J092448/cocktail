package com.ontherocks.tlqkf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/favicon.ico", "/css/**", "/js/**", "/static/**",
                                "/calculate/**", "/calendar/**", "/findId", "/findPw",
                                "/orderingFrm/**", "/api/**", "/error")
                        .permitAll()  // 🔹 인증 없이 접근 가능
                        .anyRequest().authenticated() // 🔹 나머지는 인증 필요
                )
                .anonymous(anonymous -> anonymous
                        .principal("guestUser")  // 🔹 인증되지 않은 사용자를 "guestUser"로 처리
                        .authorities("ROLE_GUEST")  // 🔹 기본 권한 설정
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
