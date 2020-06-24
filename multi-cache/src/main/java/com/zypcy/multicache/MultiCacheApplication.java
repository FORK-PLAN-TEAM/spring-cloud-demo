package com.zypcy.multicache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMethodCache(basePackages = "com.zypcy.multicache")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class MultiCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiCacheApplication.class, args);
    }

}
