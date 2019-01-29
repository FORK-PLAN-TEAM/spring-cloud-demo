package com.zypcy.springcloud.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class IndexController {

    @Value("${springcloud.book.config:123}")
    private String book;

    @RequestMapping("/getBookConfig")
    public String getBookConfig(){
        return book;
    }
}
