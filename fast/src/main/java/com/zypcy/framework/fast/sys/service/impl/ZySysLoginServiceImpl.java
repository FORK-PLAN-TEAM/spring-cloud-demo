package com.zypcy.framework.fast.sys.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.common.response.ResultEnum;
import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.zypcy.framework.fast.sys.mapper.ZySysUserMapper;
import com.zypcy.framework.fast.sys.service.IZySysLoginService;
import com.zypcy.framework.fast.sys.service.IZySysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ZySysLoginServiceImpl implements IZySysLoginService {

    @Autowired
    ZySysUserMapper userMapper;
    @Autowired
    IZySysUserRoleService userRoleService;

    /**
     * 用户登录
     * @param platform 登录平台：Pc、Wx、App
     * @param userAccount 登录帐号
     * @param userPwd 登录密码
     * @return
     */
    @Override
    public ResponseModel login(String platform , String userAccount, String userPwd) {
        ResponseModel model = ResponseModel.failInstance();
        if(StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(userPwd)){
            model.setResultMessage("请输入登录帐号或密码");
            return model;
        }

        ZySysUser queryUser = new ZySysUser();
        queryUser.setUserAccount(userAccount);
        //queryUser.setUserPwd(userPwd);
        Wrapper<ZySysUser> wrapper = new QueryWrapper<>(queryUser);
        ZySysUser sysUser = userMapper.selectOne(wrapper);
        if(sysUser != null){
            if(sysUser.getState()){
                model.setResultMessage("该账号已被禁用，请联系管理员");
                return model;
            }
            //用登录密码加盐做md5，再与数据库中保存的密码对比
            String dbPwd = sysUser.getUserPwd();
            String salt = sysUser.getSalt();
            if(dbPwd.equals(SecureUtil.md5(userPwd + salt))){
                sysUser.setLoginTime(System.currentTimeMillis());
                sysUser.setLoginPlatform(platform);
                String token = IdUtil.simpleUUID();
                updateLoginIInfo(token , sysUser);
                model.setResultCode(ResultEnum.SUCCESS.getResultCode());
                model.setResultMessage("登录成功");
                model.setResultObj(token);
            }else {
                model.setResultMessage("请使用正确的登录密码");
            }
            return model;
        }else {
            model.setResultMessage("该账号不存在，请使用正确的登录帐号");
            return model;
        }
    }

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
