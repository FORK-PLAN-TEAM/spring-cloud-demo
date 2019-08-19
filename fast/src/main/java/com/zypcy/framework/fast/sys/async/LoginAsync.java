package com.zypcy.framework.fast.sys.async;

import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.zypcy.framework.fast.sys.service.IZySysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步保存用户登录cookie信息
 */
@Service
public class LoginAsync {

    @Autowired
    IZySysUserRoleService userRoleService;

    /**
     * 异步任务做其它事件
     * 1.把登录用户信息记录到内存中
     * 2.把登录用户的角色信息记录到内存中
     */
    @Async
    public void updateLoginIInfo(String token , ZySysUser sysUser){
        ZySysLoginInfo userInfo = new ZySysLoginInfo();
        userInfo.setSysUser(sysUser);

        //获取用户拥有的角色
        userInfo.setUserRoles(userRoleService.getUserRoles(sysUser.getUserId()));

        //存储信息到缓存
        UserLoginCache.saveUserLoginInfo(token , userInfo);//存储token与用户信息
    }

}
