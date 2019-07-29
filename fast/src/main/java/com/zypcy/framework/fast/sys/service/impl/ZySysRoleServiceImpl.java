package com.zypcy.framework.fast.sys.service.impl;

import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.mapper.ZySysRoleMapper;
import com.zypcy.framework.fast.sys.service.IZySysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Service
public class ZySysRoleServiceImpl extends ServiceImpl<ZySysRoleMapper, ZySysRole> implements IZySysRoleService {

    @Autowired ZySysRoleMapper roleMapper;

    @Override
    public int add(ZySysRole role) {
        role.setIsdel(false);
        role.setCreateUserid(ContextHolder.getUserId());
        role.setCreateUsername(ContextHolder.getUserName());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateUserid(ContextHolder.getUserId());
        role.setUpdateUsername(ContextHolder.getUserName());
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.insert(role);
    }

    @Override
    public boolean deleteBatchById(String roleId) {
        return roleMapper.deleteBatchById(roleId) > 0 ? true : false;
    }
}
