package com.zypcy.springcloud.simplefeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "search-github" , url = "https://api.github.com")
public interface IIndexFeignService {

    @RequestMapping(value = "/search/repositories" , method = RequestMethod.GET)
    String search(@RequestParam("q") String query);
}
