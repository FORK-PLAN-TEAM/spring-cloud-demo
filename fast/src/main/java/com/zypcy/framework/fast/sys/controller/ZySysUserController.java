package com.zypcy.framework.fast.sys.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 * @author zhuyu
 * @since 2019-06-14
 */
@RestController
@RequestMapping("/sys/user")
public class ZySysUserController {

    @ApiOperation(value = "用户列表页"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ModelAndView list(){
        return new ModelAndView("sys/user_list");
    }

}
