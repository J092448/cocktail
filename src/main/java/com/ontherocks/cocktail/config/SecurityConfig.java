package com.ontherocks.cocktail.config;

import com.ontherocks.cocktail.Dao.AdminDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Slf4j
public class SecurityConfig {
    @Autowired
    private AdminDao aDao;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.formLogin(form -> form.loginPage("/")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    String role = authentication.getAuthorities().stream()
                            .findFirst().get().getAuthority(); //"ROLE_ADMIN" 형식
                    if ("ROLE_USER".equals(role)) {
                        response.sendRedirect("/showNotice");
                    }else if ("ROLE_ADMIN".equals(role)) {
                        response.sendRedirect("/admin/main");
                    }
                })
                .failureHandler((request, response, exception) -> {
                    //시큐리티가 DisabledException을 InternalAuthenticationServiceException로 감싼다
                    //DisabledException이 탐지되지 않아 정지 계정이라는 메시지가 전달되지 않음
                    Throwable cause = exception.getCause(); //exception.getCause() 검사하여 내부의 DisabledException 탐지
                    if (cause instanceof DisabledException){
                        request.getSession().setAttribute("msg","정지된 계정입니다. 관리자에게 문의하세요.");
                        response.sendRedirect("/");
                    }else { //그 밖의 예외
                        request.getSession().setAttribute("msg","아이디 또는 비밀번호를 확인하세요.");
                        response.sendRedirect("/");
                    }
                })
        );
        http.logout(logout -> logout.logoutUrl("/logout")
                .logoutSuccessUrl("/"));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
