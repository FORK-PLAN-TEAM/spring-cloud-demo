package com.zypcy.framework.fast.sys.cache;

import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.common.util.RedisUtil;
import com.zypcy.framework.fast.sys.constant.InitLoaderConstant;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据配置文件中的配置，存储登录会话信息
 * 登录会话信息存储方式，local，redis
 * sys.login.sessionStickType=local
 * local：本地 map 存储用户登录信息
 * redis：必须配置redis远程地址与帐号
 */
public class UserLoginCache {

    /**
     * 本地内存缓存
     */
    private static Map<String, ZySysLoginInfo> userMap = new HashMap<>();

    /**
     * 获取所有登录信息
     *
     * @return
     */
    public static Map<String, ZySysLoginInfo> getAllUserInfo() {
        if (InitLoaderConstant.SessionStickType.equals("local")) {
            return userMap;
        } else {
            return RedisUtil.Hash.getAll(ZySysLoginInfo.class, KeyConstant.Sys_Token);
        }
    }

    /**
     * 存储用户登录
     *
     * @param token
     * @param userInfo
     */
    public static void saveUserLoginInfo(String token, ZySysLoginInfo userInfo) {
        if (InitLoaderConstant.SessionStickType.equals("local")) {
            String key = KeyConstant.Sys_Token + token;
            userMap.put(key, userInfo);
        } else {
            RedisUtil.Hash.put(KeyConstant.Sys_Token, token, userInfo);
        }
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    public static ZySysLoginInfo getUserLoginInfo(String token) {
        ZySysLoginInfo userInfo = null;
        if (InitLoaderConstant.SessionStickType.equals("local")) {
            String key = KeyConstant.Sys_Token + token;
            userInfo = userMap.get(key);
            ;
        } else {
            String result = RedisUtil.Hash.get(KeyConstant.Sys_Token, token);
            if (!StringUtils.isEmpty(result)) {
                userInfo = JSON.parseObject(result, ZySysLoginInfo.class);
            }
        }
        return userInfo;
    }

    /**
     * 清除登录信息
     *
     * @param token
     */
    public static void removeLoginInfo(String token) {
        try {
            if (InitLoaderConstant.SessionStickType.equals("local")) {
                userMap.remove(token);
            } else {
                RedisUtil.Hash.del(KeyConstant.Sys_Token, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
