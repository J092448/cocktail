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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    @Autowired
//    private CustomUserDetailsService userDetailsService; // CustomUserDetailsService를 주입받음

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApplicationEventPublisher eventPublisher) throws Exception {
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
                        //.defaultSuccessUrl("/dashboard",true) // 로그인 성공 후 리다이렉트
                        .successHandler((request, response, authentication) -> {
                            //로그인에 성공하면 실행 -> 로그인 성공 이벤트(AuthenticationSuccessEvent) 발생
                            //AdminService를 직접 주입받으면 순환참조 발생 @EventListener로 이벤트만 발생시키기
                            eventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
                            String role = authentication.getAuthorities().stream()
                                    .findFirst().get().getAuthority(); //"ROLE_ADMIN" 형식
                            if ("ROLE_USER".equals(role)) {
                                response.sendRedirect("/dashboard"); //업체회원은 dashboard 로 이동
                            }else if ("ROLE_ADMIN".equals(role)) {
                                response.sendRedirect("/admin/main"); //관리자는 admin/main 으로 이동
                            }
                        })
                        //.failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트
                        .failureHandler((request, response, exception) -> {
                            //시큐리티가 DisabledException을 InternalAuthenticationServiceException로 감싼다
                            //DisabledException이 탐지되지 않아 정지 계정이라는 메시지가 전달되지 않음
                            Throwable cause = exception.getCause(); //exception.getCause() 검사하여 내부의 DisabledException 탐지
                            if (cause instanceof DisabledException){
                                response.sendRedirect("/login?suspended"); //정지된 계정으로 로그인 시
                            }else {
                                response.sendRedirect("/login?error=true"); //로그인 실패 시
                            }
                        })
                        .permitAll()
                );
                http.logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
                //.userDetailsService(userDetailsService); // UserDetailsService 등록

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 PasswordEncoder
    }
}
