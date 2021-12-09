package com.zypcy.springcloud.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {


    public static void main(String[] args) {
        System.out.println("打印日志");//添加打印日志
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
