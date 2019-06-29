package com.zypcy.framework.fast;

import com.zypcy.framework.fast.common.config.SpringContextApplication;
import com.zypcy.framework.fast.common.response.ResponseBodyWrapFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan({"com.zypcy.framework.fast.sys.mapper" , "com.zypcy.framework.fast.bus.mapper"})
public class FastApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastApplication.class, args);
	}

	/**
	 * 启用统一结果返回功能
	 * @return
	 */
	@Bean
	public ResponseBodyWrapFactory getResponseBodyWrapFactory(){
		return new ResponseBodyWrapFactory();
	}
}
