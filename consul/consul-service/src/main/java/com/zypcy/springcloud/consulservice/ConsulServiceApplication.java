package com.zypcy.springcloud.consulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling //启用后，会定时拉取配置
@EnableConfigurationProperties({StudentConfig.class})
public class ConsulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulServiceApplication.class, args);
    }

}
