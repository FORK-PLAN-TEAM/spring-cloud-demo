package com.zypcy.framework.fast.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.entity.ZySysUserRole;
import com.zypcy.framework.fast.sys.mapper.ZySysUserRoleMapper;
import com.zypcy.framework.fast.sys.service.IZySysRoleService;
import com.zypcy.framework.fast.sys.service.IZySysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Service
public class ZySysUserRoleServiceImpl extends ServiceImpl<ZySysUserRoleMapper, ZySysUserRole> implements IZySysUserRoleService {

    @Autowired ZySysUserRoleMapper userRoleMapper;
    @Autowired IZySysRoleService roleService;

    @Override
    public List<ZySysRole> getUserRoles(String userId) {
        if("admin".equals(userId)){
            //如果是超级管理员则获取所有角色
            ZySysRole role = new ZySysRole();
            role.setIsdel(false);
            return roleService.list(new QueryWrapper<>(role));
        }else {
            //否则获取用户自己用户的角色
            return userRoleMapper.getUserRoles(userId);
        }
    }
}
