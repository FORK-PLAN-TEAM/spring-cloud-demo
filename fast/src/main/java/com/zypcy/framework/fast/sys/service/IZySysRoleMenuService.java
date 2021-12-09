package com.zypcy.framework.fast.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface IZySysRoleMenuService extends IService<ZySysRoleMenu> {

    /**
     * 根据角色获取菜单树
     *
     * @param roleId
     * @return
     */
    List<ZySysTree> getRoleMenus(String roleId);
}
