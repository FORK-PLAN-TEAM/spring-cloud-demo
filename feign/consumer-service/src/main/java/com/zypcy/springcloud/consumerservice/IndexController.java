package com.zypcy.springcloud.consumerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired private HelloFeignService feignService;

    @RequestMapping(value = "/hello" , method = RequestMethod.GET)
    public String hello(String name){
        return feignService.hello(name);
    }
}
