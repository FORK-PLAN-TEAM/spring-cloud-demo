package com.zypcy.springcloud.servicec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceCApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCApplication.class, args);
	}

}

