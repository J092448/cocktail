package com.ontherocks.tlqkf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebMvc  // ✅ 웹 MVC 설정 활성화
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/calculate");
        registry.addViewController("/calendar").setViewName("/calendar"); // /calendar 요청 시 calendar.html 렌더링
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(stringConverter);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // ✅ 정적 리소스 매핑 설정 추가
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");
    }

    // ✅ CORS 설정 추가
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
                        .allowedOrigins("*")  // 모든 출처에서 접근 가능
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                        .allowCredentials(false);
            }
        };
    }
}
