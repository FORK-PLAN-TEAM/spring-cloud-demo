package com.zypcy.framework.fast.common.util;

public class LoginExpiresUtil {

    /**
     * 判断token是否过期
     * @param expireTime 过期时间
     * @param tokenTime token生成时间
     * @param loginPlatform 登录平台：Pc、Wx、Android、IOS
     * @return
     */
    public static boolean isExpire(long expireTime, long tokenTime , String loginPlatform) {
        long currentTime = System.currentTimeMillis() / (60 * 1000); //单位分钟，进行计算
        tokenTime = tokenTime / (60 * 1000);//单位分钟，进行计算
        //Pc过期时间根据配置文件中的tokenExpireTime来，其他客户端token 30天过期
        long time = "Pc".equals(loginPlatform) ? (expireTime) : (60 * 24 * 30);
        if ((currentTime - tokenTime) > time) {
            //已过期
            return true;
        }

        return false;
    }

}
