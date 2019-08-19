package com.zypcy.framework.fast.wx.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 微信端 - 底部菜单页面
 */
@RequestMapping("/wx/index")
@RestController
public class WxIndexController {

    @ApiOperation(value = "首页", notes = "页面", httpMethod = "GET")
    @GetMapping
    public ModelAndView index(ModelMap map) {
        return new ModelAndView("wx/index");
    }

    @ApiOperation(value = "修能", notes = "页面", httpMethod = "GET")
    @GetMapping("/xn")
    public ModelAndView xn(ModelMap map) {
        return new ModelAndView("wx/menu_xn");
    }

    @ApiOperation(value = "记账", notes = "页面", httpMethod = "GET")
    @GetMapping("/jz")
    public ModelAndView fx(ModelMap map) {
        return new ModelAndView("wx/menu_jz");
    }

    @ApiOperation(value = "我", notes = "页面", httpMethod = "GET")
    @GetMapping("/my")
    public ModelAndView my(ModelMap map) {
        return new ModelAndView("wx/menu_my");
    }
}
