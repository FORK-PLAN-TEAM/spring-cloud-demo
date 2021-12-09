package com.zypcy.springcloud.orderserviceprometheus.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Random;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Value("${server.port}")
    private String port;

    @Autowired
    MeterRegistry registry;
    private Counter counter;
    private DistributionSummary summary;

    @PostConstruct
    private void init() {
        counter = registry.counter("requests_zhuyu_total", "order", "zhuyu");
        summary = registry.summary("order_amount_total", "totalAmount", "totalAmount");
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello , port : " + port;
    }

    @RequestMapping("/zhuyu")
    public String zhuyu() {
        counter.increment();
        return "zhuyu , port : " + port;
    }

    @RequestMapping("/error")
    public String error() {
        if (true) {
            throw new RuntimeException("主动报错");
        }
        return "controller error";
    }

    //订单方法，随机产生订单金额
    @RequestMapping("/order")
    public String order() {
        Random random = new Random();
        summary.record(random.nextInt(100));
        return "order , port : " + port;
    }
}
