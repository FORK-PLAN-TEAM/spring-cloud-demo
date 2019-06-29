package com.zypcy.framework.fast.sys.factory;

import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.common.config.SpringContextApplication;
import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.constant.InitLoaderConstant;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

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
            StringRedisTemplate redisTemplate = SpringContextApplication.getBean(StringRedisTemplate.class);
            redisTemplate.opsForHash().put(KeyConstant.Redis.Sys_Token , token , JSON.toJSONString(sysUser));
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
            StringRedisTemplate redisTemplate = SpringContextApplication.getBean(StringRedisTemplate.class);
            Object result = redisTemplate.opsForHash().get(KeyConstant.Redis.Sys_Token , token);
            if(result != null){
                sysUser = JSON.parseObject(result.toString() , ZySysUser.class);
            }
        }
        return sysUser;
    }
}
