package com.zypcy.dynamicdatabase;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zypcy.dynamicdatabase.repository")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class DynamicDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDatabaseApplication.class, args);
    }

}
