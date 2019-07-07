package com.zypcy.framework.fast.sys.factory;

import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.common.config.SpringContextApplication;
import com.zypcy.framework.fast.common.util.RedisUtil;
import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.constant.InitLoaderConstant;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
     * @param sysUser
     */
    public static void saveUserLoginInfo(String token , ZySysUser sysUser){
        if(InitLoaderConstant.SessionStickType.equals("local")){
            UserLoginCache.saveUserLoginInfo(KeyConstant.Local.Sys_Token , token , sysUser);
        }else {
            RedisUtil.Hash.put(KeyConstant.Redis.Sys_Token , token , sysUser);
        }
    }

    /**
     * 根据token获取用户登录信息
     * @param token
     * @return
     */
    public static ZySysUser getUserLoginInfo(String token){
        ZySysUser sysUser = null;
        if(InitLoaderConstant.SessionStickType.equals("local")){
            sysUser = UserLoginCache.getUserLoginInfo(KeyConstant.Local.Sys_Token , token);
        }else {
            String result = RedisUtil.Hash.get(KeyConstant.Redis.Sys_Token , token);
            if(!StringUtils.isEmpty(result)){
                sysUser = JSON.parseObject(result, ZySysUser.class);
            }
        }
        return sysUser;
    }
}
