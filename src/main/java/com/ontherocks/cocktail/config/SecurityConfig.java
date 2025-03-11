package com.ontherocks.cocktail.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("🔒 Security Config Loaded!");

        http
                .authorizeHttpRequests(auth -> {
                    logger.info("🔓 Public Access: /csPages/**");
                    auth.requestMatchers("/images/**", "/css/**", "/js/**", "/menu/**",
                            "/cocktail/templates/**", "/menuOrder.html",
                            "/csPages/**").permitAll();  // 고객 관련 페이지는 로그인 없이 접근 가능

                    auth.anyRequest().authenticated();  // 그 외에는 로그인 필요
                })
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .formLogin(form -> {
                    logger.info("🔑 Login Page: /login");
                    form.loginPage("/login").permitAll();
                })  // 로그인 페이지 설정
                .logout(logout -> {
                    logger.info("🚪 Logout Configured!");
                    logout.logoutUrl("/logout").permitAll();
                });  // 로그아웃 설정

        return http.build();
    }
}
