package com.zypcy.framework.fast.common.config;

import com.zypcy.framework.fast.sys.entity.ZySysUser;

/**
 * 请求时，通过token获取用户信息
 * 并将用户信息存储到线程上下文中
 */
public class ContextHolder {

    private static ThreadLocal<ZySysUser> userInfo = new ThreadLocal<ZySysUser>();

    /**
     * 设置用户信息
     * @param user
     */
    public static void setUserInfo(ZySysUser user){
        userInfo.set(user);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static ZySysUser getUserInfo(){
        return userInfo.get();
    }

    /**
     * 获取当前用户Id
     * @return
     */
    public static String getUserId(){
        ZySysUser user = userInfo.get();
        if(user != null){
            return user.getUserId();
        }else {
            return "";
        }
    }

    /**
     * 获取当前用户Name
     * @return
     */
    public static String getUserName(){
        ZySysUser user = userInfo.get();
        if(user != null){
            return user.getUserName();
        }else {
            return "";
        }
    }
}
