package com.zypcy.elk.logcollect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class LogcollectApplication {

    Logger logger = LoggerFactory.getLogger(LogcollectApplication.class);

    @GetMapping("log")
    public void test() {
        logger.debug("message debug");
        logger.info("message info");
        logger.warn("message warn");
        logger.error("message error");
        logger.info("测试初始一些日志吧！");
    }

    public static void main(String[] args) {
        SpringApplication.run(LogcollectApplication.class, args);
    }

}
