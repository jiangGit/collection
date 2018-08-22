package com.facetuis.server.admin.config;

import com.facetuis.server.admin.interceptor.AdminAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminInterceptorConfig implements WebMvcConfigurer {

    @Bean
    AdminAuthInterceptor adminAuthInterceptor(){
        return new AdminAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(adminAuthInterceptor())
                .excludePathPatterns("/error")
                .excludePathPatterns("/static/*")
                .addPathPatterns("/admin/**");


    }
}
