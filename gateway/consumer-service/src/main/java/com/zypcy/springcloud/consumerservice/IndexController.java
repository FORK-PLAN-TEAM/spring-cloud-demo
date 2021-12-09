package com.zypcy.springcloud.consumerservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/hello")
    public String hello(String name) {
        return "hi " + name;
    }

    @RequestMapping("/timeout")
    public String timeout() {
        try {
            //睡5秒，网关Hystrix3秒超时
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "timeout";
    }
}
