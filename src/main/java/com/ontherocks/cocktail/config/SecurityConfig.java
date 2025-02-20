package com.ontherocks.cocktail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/images/**", "/css/**", "/js/**", "/menu/**", "/cocktail/templates/**", "/menuOrder.html").permitAll() // 인증 없이 접근할 수 있는 리소스 추가
//                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
//                )
//                .logout(logout -> logout.permitAll()) // 로그아웃 기능 허용
        http.csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화

        return http.build();
    }
}
