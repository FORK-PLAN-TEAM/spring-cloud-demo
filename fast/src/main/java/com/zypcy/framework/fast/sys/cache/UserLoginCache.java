package com.zypcy.framework.fast.sys.cache;

import com.zypcy.framework.fast.sys.entity.ZySysUser;

import java.util.HashMap;

/**
 * 本地 map 存储用户登录信息
 */
public class UserLoginCache {

    private static HashMap<String , ZySysUser> userMap = new HashMap<>();

    /**
     * 存储用户登录
     * @param hashKey
     * @param token
     * @param sysUser
     */
    public static void saveUserLoginInfo(String hashKey , String token , ZySysUser sysUser){
        String key = hashKey + token;
        userMap.put(key , sysUser);
    }

    /**
     * 根据token获取用户信息
     * @param hashKey
     * @param token
     * @return
     */
    public static ZySysUser getUserLoginInfo(String hashKey , String token){
        String key = hashKey + token;
        return userMap.get(key);
    }
}