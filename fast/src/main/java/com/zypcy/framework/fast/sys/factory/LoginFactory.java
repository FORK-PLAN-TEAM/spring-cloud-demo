package com.zypcy.framework.fast.sys.factory;

import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.common.util.RedisUtil;
import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.constant.InitLoaderConstant;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.entity.ZySysLoginInfo;
import org.springframework.util.StringUtils;

/**
 * 登录帮助类
 * 根据系统配置存储类型，如果是local则本地内存存储，否则是redis存储
 */
public class LoginFactory {

    /**
     * 存储登录信息
     * 根据系统配置存储类型，如果是local则本地内存存储，否则是redis存储
     * @param token
     * @param userInfo
     */
    public static void saveUserLoginInfo(String token , ZySysLoginInfo userInfo){
        if(InitLoaderConstant.SessionStickType.equals("local")){
            UserLoginCache.saveUserLoginInfo(KeyConstant.Local.Sys_Token , token , userInfo);
        }else {
            RedisUtil.Hash.put(KeyConstant.Redis.Sys_Token , token , userInfo);
        }
    }

    /**
     * 根据token获取用户登录信息
     * @param token
     * @return
     */
    public static ZySysLoginInfo getUserLoginInfo(String token){
        ZySysLoginInfo userInfo = null;
        if(InitLoaderConstant.SessionStickType.equals("local")){
            userInfo = UserLoginCache.getUserLoginInfo(KeyConstant.Local.Sys_Token , token);
        }else {
            String result = RedisUtil.Hash.get(KeyConstant.Redis.Sys_Token , token);
            if(!StringUtils.isEmpty(result)){
                userInfo = JSON.parseObject(result, ZySysLoginInfo.class);
            }
        }
        return userInfo;
    }
}
