package com.zypcy.springcloud.consumerservice;

import org.springframework.stereotype.Service;

/**
 * hystrix服务降级处理，防止因超时、异常等导致的服务调用雪崩
 */
@Service
public class HelloFeignFallbackService implements HelloFeignService {
    @Override
    public String hello(String name) {
        return "未找到" + name;
    }

    @Override
    public Person createPerson(Person person) {
        return null;
    }
}
