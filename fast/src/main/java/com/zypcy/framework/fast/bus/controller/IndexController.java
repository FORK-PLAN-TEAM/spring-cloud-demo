package com.zypcy.framework.fast.bus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "bus-业务首页控制器")
@RestController
public class IndexController {

    @ApiOperation(value = "检查系统是否健康", notes = "返回health表示系统健康", httpMethod = "GET")
    @RequestMapping("/health")
    public String health(){
        return "success";
    }

    //系统首页
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
