package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.common.response.ResponseModel;

/**
 * 用户信息相关接口
 * 1.用户登录
 */
public interface IZySysLoginService {

    /**
     * 用户登录
     * @param userAccount 登录帐号
     * @param userPwd 登录密码
     * @return
     */
    ResponseModel login(String userAccount , String userPwd);

}
