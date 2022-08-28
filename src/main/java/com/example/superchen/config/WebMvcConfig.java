package com.example.superchen.config;

import com.example.superchen.aop.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 *
 * 将拦截器注册到容器中(访问控制)
 * @Author chen
 * @Date  18:05
 * @Param
 * @Return
 * @Since version-11

 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Bean
    public SessionInterceptor tokenVerifyInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionInterceptor);
        registry.addInterceptor(tokenVerifyInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}