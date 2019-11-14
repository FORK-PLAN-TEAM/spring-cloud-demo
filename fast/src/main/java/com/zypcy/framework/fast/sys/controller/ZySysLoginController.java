package com.zypcy.framework.fast.sys.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.codec.Base64;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.common.util.LogUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "sys-登录")
@RestController
@RequestMapping("sys/login")
public class ZySysLoginController {

    @Autowired
    IZySysLoginService loginService;

    private static final String VerifyCode = "VerifyCode";

    @ApiOperation(value = "返回验证码"  , notes = "api接口", httpMethod = "GET")
    @RequestMapping(value = "/verifyCode" , method = RequestMethod.GET)
    public void verifyCode(HttpServletRequest request, HttpServletResponse response){
        try {
            //定义图形验证码的长、宽、验证码字符数、干扰元素个数
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 60, 4, 16);
            //图形验证码写出，可以写出到文件，也可以写出到流
            //captcha.write(response.getOutputStream());
            String code = captcha.getCode();
            //LogUtil.info("VerifyCode："+ code);
            request.getSession().setAttribute(VerifyCode, code);//将VerifyCode绑定session
            response.setHeader("Pragma", "no-cache");//设置响应头
            response.setHeader("Cache-Control", "no-cache");//设置响应头
            response.setDateHeader("Expires", 0);//在代理服务器端防止缓冲
            response.setContentType("image/jpeg");//设置响应内容类型
            //response.getOutputStream().write(captcha.getImageBase64().getBytes());
            captcha.write(response.getOutputStream());//输出到页面
            response.getOutputStream().close();
            response.getOutputStream().flush();
        } catch (IOException e) {
            LogUtil.info(e.getMessage());
        }
    }


    @ApiOperation(value = "登录接口，用户名和密码请用base64加密后传入，成功返回token"  , notes = "api接口", httpMethod = "POST")
    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public ResponseModel login(@ApiParam(name = "登录平台：Pc、Wx、Android、IOS") String platform , @ApiParam(name = "用户名") String userAccount ,
                               @ApiParam(name = "密码") String userPwd , @ApiParam(name = "验证码") String verifyCode , HttpServletRequest request){
        if(StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(userPwd)){
            throw new BusinessException("请输入用户名或密码");
        }
        if(platform.equals("Pc")){
            if(StringUtils.isEmpty(verifyCode)){
                throw new BusinessException("请输入验证码");
            }
            if(request.getSession().getAttribute(VerifyCode) != null){
                String sesionVerifyCode = request.getSession().getAttribute(VerifyCode).toString();
                if(!sesionVerifyCode.equals(verifyCode)){
                    throw new BusinessException("请输入正确的验证码");
                }
            }
        }
        return loginService.login(platform , Base64.decodeStr(userAccount) , Base64.decodeStr(userPwd));
    }

}
