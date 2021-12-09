package com.zypcy.sharding.sublibrarytable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan("com.zypcy.sharding.sublibrarytable.repository")
@SpringBootApplication
public class SubLibraryTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubLibraryTableApplication.class, args);
    }
}
