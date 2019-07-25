package com.zypcy.framework.fast.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.dto.ZySysRoleMenuTree;
import com.zypcy.framework.fast.sys.entity.ZySysRoleMenu;
import com.zypcy.framework.fast.sys.service.IZySysRoleMenuService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 角色菜单 前端控制器
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@RestController
@RequestMapping("/sys/role_menu")
public class ZySysRoleMenuController {

    @Autowired
    IZySysRoleMenuService roleMenuService;

    @ApiOperation(value = "返回角色菜单页面", notes = "页面", httpMethod = "GET")
    @GetMapping("menu")
    public ModelAndView roleMenu(String roleId, ModelMap map) {
        //查询角色已绑定菜单
        map.addAttribute("roleMenus", roleMenuService.getRoleMenus(roleId));
        return new ModelAndView("sys/role_menu");
    }

    @Transactional
    @ApiOperation(value = "给角色添加菜单", notes = "api接口", httpMethod = "POST")
    @PostMapping("save")
    public boolean save(@ApiParam(value = "菜单集合") @RequestBody ZySysRoleMenuTree roleMenuTree) {
        ZySysRoleMenu queryMenu = new ZySysRoleMenu();
        queryMenu.setRoleId(roleMenuTree.getRoleId());
        //先删除该角色下所有菜单
        roleMenuService.remove(new QueryWrapper<>(queryMenu));

        //再给角色新增菜单
        Collection<ZySysRoleMenu> list = new ArrayList<>();
        for (String menuId : roleMenuTree.getMenuIds()) {
            ZySysRoleMenu roleMenu = new ZySysRoleMenu();
            roleMenu.setRoleId(roleMenuTree.getRoleId());
            roleMenu.setId(IdWorker.getId());
            roleMenu.setMenuId(menuId);
            list.add(roleMenu);
            roleMenu = null;
        }
        return roleMenuService.saveBatch(list);
    }

}
