package com.facetuis.server.app.config;

import com.facetuis.server.app.interceptor.AuthInterceptor;
import com.facetuis.server.app.interceptor.LoggerInterceptor;
import com.facetuis.server.app.interceptor.SignInterceptor;
import com.facetuis.server.app.interceptor.UserLevelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    LoggerInterceptor loggerInterceptor(){
        return new LoggerInterceptor();
    }

    @Bean
    SignInterceptor signInterceptor(){
        return new SignInterceptor();
    }

    @Bean
    AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Bean
    UserLevelInterceptor userLevelInterceptor(){
        return new UserLevelInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor())
                .excludePathPatterns("/error")
                .excludePathPatterns("/static/*")
                .addPathPatterns("/**");
        // 校验授权
        registry.addInterceptor(authInterceptor())
                .excludePathPatterns("/error")
                .excludePathPatterns("/static/*")
                .addPathPatterns("/1.0/**");
        // 验证签名
        registry.addInterceptor(signInterceptor())
                .excludePathPatterns("/error")
                .excludePathPatterns("/static/*")
                .addPathPatterns("/1.0/**");
        // 用户级别校验
        registry.addInterceptor(userLevelInterceptor())
                .excludePathPatterns("/error")
                .excludePathPatterns("/static/*")
                .addPathPatterns("/1.0/**");

    }
}
