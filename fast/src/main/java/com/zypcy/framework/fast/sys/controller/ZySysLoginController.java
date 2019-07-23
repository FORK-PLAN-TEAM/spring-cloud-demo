package com.zypcy.framework.fast.sys.controller;

import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.sys.service.IZySysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "Sys-登录")
@RestController
@RequestMapping("sys/login")
public class ZySysLoginController {

    @Autowired
    IZySysLoginService loginService;

    @ApiOperation(value = "登录，成功返回token"  , notes = "api接口", httpMethod = "POST")
    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public ResponseModel login(String userAccount , String userPwd){
        return loginService.login(userAccount , userPwd);
    }

}
