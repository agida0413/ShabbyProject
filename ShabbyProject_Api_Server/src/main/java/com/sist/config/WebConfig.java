package com.sist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sist.intercepter.DuplicateRequestInterceptor;

@Configuration
public class WebConfig {
// 인터셉터 등록 (중복호출 방지)
    @Bean
    public WebMvcConfigurer webMvcConfigurer(DuplicateRequestInterceptor duplicateRequestInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(duplicateRequestInterceptor)
                        .addPathPatterns("/**");
            }
        };
    }
}