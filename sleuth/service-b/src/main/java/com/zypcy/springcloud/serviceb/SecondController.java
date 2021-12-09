package com.zypcy.springcloud.serviceb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SecondController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/Second")
    public String Second() {
        System.out.println("Second...");

        String b = " service-b hello , ";

        //远程调用service-c
        String resultC = remoteCallServiceC();

        //远程调用service-d
        String resultD = remoteCallServiceD();

        return b + resultC + resultD;
    }

    //远程调用service-c
    private String remoteCallServiceC() {
        String url = "http://service-c/Three";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    //远程调用service-d
    private String remoteCallServiceD() {
        String url = "http://service-d/Four";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }
}
