package com.zypcy.framework.fast.ScheduleTask;

import cn.hutool.core.date.DateUtil;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.common.util.LoginExpiresUtil;
import com.zypcy.framework.fast.common.util.RedisUtil;
import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

/**
 * 清空过期登录信息
 */
@Configuration
public class ClearLoginSessionTask {

    //获取配置文件中的token过期时间, 单位分钟，默认120
    @Value("${sys.token.expireTime:120}")
    private long tokenExpireTime;

    //添加定时任务，例如：60秒执行一次
    @Async
    @Scheduled(cron = "0/60 * * * * ?")
    public void configureTasks() {
        LogUtil.info("ClearLoginSessionTask执行定时任务时间: " + DateUtil.now());
        Map<String , ZySysLoginInfo> maps = UserLoginCache.getAllUserInfo();
        for(Map.Entry<String, ZySysLoginInfo> entry : maps.entrySet()){
            String token = entry.getKey();
            ZySysLoginInfo loginInfo = entry.getValue();
            //登录信息过期后从内存中移除
            if(loginInfo != null && loginInfo.getSysUser() != null && LoginExpiresUtil.isExpire(tokenExpireTime , loginInfo.getSysUser().getLoginTime() , loginInfo.getSysUser().getLoginPlatform())){
                UserLoginCache.removeLoginInfo(token);
            }
        }
    }
}
