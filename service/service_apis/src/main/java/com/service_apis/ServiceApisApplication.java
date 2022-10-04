package com.service_apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients    //服务调用
@EnableDiscoveryClient  //nacos注册
@SpringBootApplication
public class ServiceApisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApisApplication.class, args);
    }

}
