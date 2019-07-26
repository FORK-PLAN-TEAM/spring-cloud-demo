package com.zypcy.framework.fast.common.config;

import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.entity.ZySysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求时，通过token获取用户信息
 * 并将用户信息存储到线程上下文中
 */
public class ContextHolder {

    private static ThreadLocal<ZySysLoginInfo> userInfo = new ThreadLocal<ZySysLoginInfo>();

    /**
     * 设置用户信息
     *
     * @param user
     */
    public static void setUserInfo(ZySysLoginInfo user) {
        userInfo.set(user);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static ZySysLoginInfo getUserInfo() {
        return userInfo.get();
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static ZySysUser getSysUser() {
        ZySysLoginInfo userInfo = getUserInfo();
        if (userInfo != null && userInfo.getSysUser() != null) {
            return userInfo.getSysUser();
        } else {
            return null;
        }
    }

    /**
     * 获取当前登录用户的角色信息
     * @return
     */
    public static List<ZySysRole> getSysUserRoles(){
        ZySysLoginInfo userInfo = getUserInfo();
        if (userInfo != null && userInfo.getUserRoles() != null) {
            return userInfo.getUserRoles();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取当前用户Id
     *
     * @return
     */
    public static String getUserId() {
        ZySysUser user = getSysUser();
        if (user != null) {
            return user.getUserId();
        } else {
            return "";
        }
    }

    /**
     * 获取当前用户Name
     *
     * @return
     */
    public static String getUserName() {
        ZySysUser user = getSysUser();
        if (user != null) {
            return user.getUserName();
        } else {
            return "";
        }
    }
}
