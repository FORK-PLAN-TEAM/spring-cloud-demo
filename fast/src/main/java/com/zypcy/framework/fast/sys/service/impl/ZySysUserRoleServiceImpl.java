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

    /**
     * 获取用户创建的角色信息
     * @param userId
     * @return
     */
    @Override
    public List<ZySysRole> getUserCreateRoles(String userId) {
        ZySysRole role = new ZySysRole();
        role.setIsdel(false);
        role.setCreateUserid(userId);
        return roleService.list(new QueryWrapper<>(role));
    }

    /**
     * 获取用户已保存的角色信息
     * @param userId
     * @return
     */
    @Override
    public List<ZySysRole> getUserSaveRoles(String userId) {
        return userRoleMapper.getUserRoles(userId);
    }
}
