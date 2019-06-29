package com.zypcy.framework.fast.sys.init;

import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.sys.constant.InitLoaderConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动时执行
 */
@Component
public class loader implements ApplicationRunner {

    //登录会话信息存储方式，local，redis ，默认local
    @Value("${sys.login.sessionStickType:local}")
    private String SessionStickType;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        check();

    }

    /**
     * 检查
     */
    private void check() {
        checkSessionStickType();
    }

    /**
     * 检查登录会话信息存储方式，是本地存储还是redis存储
     */
    private void checkSessionStickType(){
        if(!SessionStickType.equals("local")){
            InitLoaderConstant.SessionStickType = "redis";
        }
    }
}
