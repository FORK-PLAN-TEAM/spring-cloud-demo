package com.example.adminclient.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @description:
 * @author: zhuyu
 * @date: 2021/3/12 11:17
 */
@RestController
public class IndexController {

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/hello")
    public String hello() {
        logger.info("hello...");
        return "hello";
    }

    @RequestMapping("/err")
    public String error() {
        throw new RuntimeException("custom error..");
    }

}
