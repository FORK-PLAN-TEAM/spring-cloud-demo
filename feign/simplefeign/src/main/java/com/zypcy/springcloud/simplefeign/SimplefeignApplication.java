package com.zypcy.springcloud.simplefeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SimplefeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplefeignApplication.class, args);
	}
}
