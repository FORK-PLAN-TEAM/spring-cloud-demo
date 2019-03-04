package com.zypcy.springcloud.servicea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {

    @Autowired private RestTemplate restTemplate;

    @RequestMapping("/index")
    public String index(){
        //2.远程调用service-b的hello方法
        String result = remoteCallServiceB();

        return " service-a start ," + result;
    }

    //远程调用service-b
    private String remoteCallServiceB(){
        String url = "http://service-b/Second";
        String result = restTemplate.getForObject(url , String.class);
        return result;
    }
}
