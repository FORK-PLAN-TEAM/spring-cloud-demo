package com.zypcy.framework.fast.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysRoleMenu;
import com.zypcy.framework.fast.sys.mapper.ZySysRoleMenuMapper;
import com.zypcy.framework.fast.sys.service.IZySysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Service
public class ZySysRoleMenuServiceImpl extends ServiceImpl<ZySysRoleMenuMapper, ZySysRoleMenu> implements IZySysRoleMenuService {

    @Autowired ZySysRoleMenuMapper roleMenuMapper;

    @Override
    public List<ZySysTree> getRoleMenus(String roleId) {
        return roleMenuMapper.getRoleMenus(roleId);
    }

}
