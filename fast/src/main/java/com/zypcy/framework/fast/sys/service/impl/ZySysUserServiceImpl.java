package com.zypcy.framework.fast.sys.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.zypcy.framework.fast.sys.entity.ZySysUserRole;
import com.zypcy.framework.fast.sys.mapper.ZySysUserMapper;
import com.zypcy.framework.fast.sys.mapper.ZySysUserRoleMapper;
import com.zypcy.framework.fast.sys.service.IZySysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Service
public class ZySysUserServiceImpl extends ServiceImpl<ZySysUserMapper, ZySysUser> implements IZySysUserService {

    @Autowired ZySysUserMapper userMapper;
    @Autowired
    ZySysUserRoleMapper userRoleMapper;

    @Transactional
    @Override
    public int add(ZySysUser user) {
        user.setSalt(IdUtil.simpleUUID());
        //新增用户时，对密码加盐后进行md5加密，登录时对输入的密码反解密
        String pwd = SecureUtil.md5(user.getUserPwd() + user.getSalt());
        user.setUserPwd(pwd);
        user.setIsdel(false);
        user.setCreateUserid(ContextHolder.getUserId());
        user.setCreateUsername(ContextHolder.getUserName());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateUserid(ContextHolder.getUserId());
        user.setUpdateUsername(ContextHolder.getUserName());
        user.setUpdateTime(LocalDateTime.now());
        int b = userMapper.insert(user);
        //保存用户拥有的角色
        saveUserRole(user.getUserId() , user.getRoleIds());
        return b;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Transactional
    @Override
    public boolean update(ZySysUser user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateUserid(ContextHolder.getUserId());
        user.setUpdateUsername(ContextHolder.getUserName());

        //删除该用户所有角色，再重新保存角色
        ZySysUserRole userRole = new ZySysUserRole();
        userRole.setUserId(user.getUserId());
        userRoleMapper.delete(new QueryWrapper<>(userRole));
        saveUserRole(user.getUserId() , user.getRoleIds());

        return userMapper.updateById(user) > 0 ? true : false;
    }

    /**
     * 修改密码
     * @return
     */
    @Override
    public boolean updatePwd(String oldPwd ,String newPwd) {
        String userId = ContextHolder.getUserId();
        ZySysUser user = userMapper.selectById(userId);
        if(user != null){
            String dbOldPwd = SecureUtil.md5(oldPwd + user.getSalt());
            if(dbOldPwd.equals(user.getUserPwd())){
                String pwd = SecureUtil.md5(newPwd + user.getSalt());
                user.setUserPwd(pwd);
                return userMapper.updateById(user) > 0 ? true : false;
            }else{
                throw new BusinessException("请输入正确的旧密码");
            }
        }
        return false;
    }

    /**
     * 保存用户的角色信息
     * @param userId
     * @param roleIds
     */
    private void saveUserRole(String userId , String roleIds){
        String[] ids = roleIds.split(",");
        for(String roleId : ids){
            ZySysUserRole userRole = new ZySysUserRole();
            userRole.setId(IdWorker.getId());
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public boolean deleteById(String userId) {
        return userMapper.deleteById(userId) > 0 ? true : false;
    }
}
