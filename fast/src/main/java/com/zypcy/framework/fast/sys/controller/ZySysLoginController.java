package com.zypcy.framework.fast.sys.controller;

import cn.hutool.core.codec.Base64;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.sys.service.IZySysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @ApiOperation(value = "登录接口，用户名和密码请用base64加密后传入，成功返回token"  , notes = "api接口", httpMethod = "POST")
    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public ResponseModel login(@ApiParam(name = "用户名") String userAccount ,@ApiParam(name = "密码") String userPwd){
        if(StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(userPwd)){
            throw new BusinessException("请传入用户名或密码");
        }
        return loginService.login(Base64.decodeStr(userAccount) , Base64.decodeStr(userPwd));
    }

}
