package com.ontherocks.cocktail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebMvc  // 웹 MVC 설정 활성화
public class WebConfig implements WebMvcConfigurer {

    // 기본 페이지 설정
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/calculate");
        registry.addViewController("/calendar").setViewName("calendar");
        registry.addViewController("/newTradeCompany").setViewName("newTradeCompany");
    }

    // JSON 변환기 설정 추가
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter()); // JSON 변환기 추가
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 문자열 인코딩 UTF-8 설정
    }

    // RestTemplate Bean 등록
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 정적 리소스 매핑 설정 추가
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

    // CORS 설정 추가
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:80") // 출처 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true); // 자격 증명 허용
    }

    // Exception Resolver 추가
    @Bean
    public ExceptionHandlerExceptionResolver exceptionResolver() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setWarnLogCategory("org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver");
        return resolver;
    }
}
