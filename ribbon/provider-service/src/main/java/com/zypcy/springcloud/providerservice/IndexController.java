package com.zypcy.springcloud.providerservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

    @GetMapping("/hello")
    public String hello(String name, HttpServletRequest request) {
        return " From Port : " + request.getServerPort() + " , hello " + name;
    }
}
