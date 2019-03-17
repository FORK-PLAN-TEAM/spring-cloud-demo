package com.zypcy.springcloud.orderserviceprometheus.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

/**
 * 全局统一异常处理
 * 捕获异常，产生异常时，统一返回错误信息
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    MeterRegistry registry;
    private Counter counter;
    @PostConstruct
    private void init(){
        counter = registry.counter("requests_error_total","status","500");
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String handle(Exception e) {
        counter.increment();//记录系统出错总数
        return "error , message : " + e.getMessage();
    }
}
