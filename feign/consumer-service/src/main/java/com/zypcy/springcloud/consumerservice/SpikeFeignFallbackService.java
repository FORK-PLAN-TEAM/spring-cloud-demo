package com.zypcy.springcloud.consumerservice;

import org.springframework.stereotype.Service;

@Service
public class SpikeFeignFallbackService implements SpikeFeignService {
    @Override
    public String health() {
        return "error";
    }

    @Override
    public String spike() {
        return "hystrix fail";
    }
}
