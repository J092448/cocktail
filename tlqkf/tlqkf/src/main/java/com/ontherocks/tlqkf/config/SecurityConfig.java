

package com.ontherocks.tlqkf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🔹 CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 🔹 CORS 설정 추가
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/favicon.ico", "/css/**", "/js/**", "/static/**",
                                "/calculate/**", "/calendar/**", "/api/data", "/findId", "/findPw",
                                "/orderingFrm/**", "/previousData","/api/**", "/error")  // 🔹 `/api/data` 허용 추가
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .anonymous(anonymous -> anonymous
                        .principal("guestUser")  // 🔹 인증되지 않은 사용자를 "guestUser"로 처리
                        .authorities("ROLE_GUEST")  // 🔹 기본 권한 설정
                );
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );

        return http.build();
    }
    // CORS 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:80")); // 모든 출처 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(false); // 자격 증명 허용하지 않음

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }
}
