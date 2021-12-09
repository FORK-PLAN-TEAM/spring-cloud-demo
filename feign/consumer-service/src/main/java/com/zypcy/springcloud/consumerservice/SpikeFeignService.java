package com.zypcy.springcloud.consumerservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "distributed-lock-service", fallback = SpikeFeignFallbackService.class)
public interface SpikeFeignService {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    String health();

    @RequestMapping(value = "/spike", method = RequestMethod.GET)
    String spike();
}
