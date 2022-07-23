package com.example.superchen.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class corsFilter1 implements WebMvcConfigurer {
    //跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 匹配所有的路径
                .allowCredentials(true) // 设置允许凭证
                .allowedHeaders("*")   // 设置请求头
                .allowedMethods("GET"); //允许所有GET 请求跨域
//                .allowedOriginPatterns("*");
    }
}
