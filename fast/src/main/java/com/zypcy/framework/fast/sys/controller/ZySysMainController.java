package com.zypcy.framework.fast.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "Sys-后台主页")
@RestController
@RequestMapping("sys/main")
public class ZySysMainController {

    @ApiOperation(value = "登录成功后返回系统后台主页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping()
    public ModelAndView main(){
        return new ModelAndView("sys/main");
    }

    @ApiOperation(value = "系统后台桌面页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("desktop")
    public ModelAndView desktop(){
        return new ModelAndView("sys/desktop");
    }
}
