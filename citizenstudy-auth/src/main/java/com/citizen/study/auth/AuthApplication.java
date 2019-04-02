package com.citizen.study.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by zhang on 2019/3/5.
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.citizen.study.auth.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}

//https://github.com/SpringCloud/spring-cloud-gateway-nacos/