package org.lfh.blog_demo.config;

import jakarta.annotation.Resource;
import org.lfh.blog_demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/api/auth/register", "/api/auth/login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }
}
