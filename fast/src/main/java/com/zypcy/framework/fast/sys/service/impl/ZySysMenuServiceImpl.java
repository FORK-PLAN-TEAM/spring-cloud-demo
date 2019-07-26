package com.zypcy.framework.fast.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.sys.entity.ZySysMenu;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.mapper.ZySysMenuMapper;
import com.zypcy.framework.fast.sys.service.IZySysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Service
public class ZySysMenuServiceImpl extends ServiceImpl<ZySysMenuMapper, ZySysMenu> implements IZySysMenuService {

    @Autowired private ZySysMenuMapper menuMapper;

    /**
     * 获取菜单树，只返回3级菜单
     * @return
     */
    @Override
    public List<ZySysTree> getMenuTrees() {
        List<ZySysTree> zySysMenus = menuMapper.getMenuTrees();
        return zySysMenus;
    }

    /**
     * 获取用户拥有的角色菜单
     * @param roleIds
     * @return
     */
    @Override
    public List<ZySysMenu> getMenusByRoleId(List<String> roleIds){
        return menuMapper.getMenusByRoleId(roleIds);
    }

    /**
     * 超级管理员获取所有菜单
     * @return
     */
    @Override
    public List<ZySysMenu> getMenusByAdmin() {
        return menuMapper.getMenusByAdmin();
    }
}
