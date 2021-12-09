package com.zypcy.springcloud.distributedlock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class DistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApplication.class, args);
    }


    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        return (Redisson) Redisson.create(config);
    }

}
