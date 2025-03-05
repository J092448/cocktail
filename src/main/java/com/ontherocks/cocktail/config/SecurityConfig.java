package com.ontherocks.cocktail.config;

import com.ontherocks.cocktail.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService; // CustomUserDetailsService를 주입받음

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
               //.csrf(csrf -> csrf
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) // CSRF 토큰 저장소 설정
                .csrf(csrf-> csrf.disable());
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login", "/register", "/css/**", "/js/**", "/findId","/findPw","/check-username","/api/phoneNumber/generateOTP", "/api/phoneNumber/verifyOTP","/changePassword").permitAll() // 로그인 및 회원가입 페이지 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .defaultSuccessUrl("/dashboard",true) // 로그인 성공 후 리다이렉트
                        .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트
                        .permitAll()
                )
                .logout(logout -> logout
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
