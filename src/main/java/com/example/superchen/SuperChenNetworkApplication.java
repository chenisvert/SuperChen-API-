package com.example.superchen;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class SuperChenNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperChenNetworkApplication.class, args);
    }

}
