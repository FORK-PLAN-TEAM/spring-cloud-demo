package com.zypcy.framework.fast.sys.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysMenu;
import com.zypcy.framework.fast.sys.service.IZySysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Api(tags = "sys-菜单管理")
@RestController
@RequestMapping("/sys/menu")
public class ZySysMenuController {

    @Autowired private IZySysMenuService menuService;

    @ApiOperation(value = "菜单列表页"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ModelAndView list(ModelMap map){
        //返回菜单树集合
        map.addAttribute("menus" , menuService.getMenuTrees());
        return new ModelAndView("sys/menu_list");
    }

    @ApiOperation(value = "图标页"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/icon")
    public ModelAndView icon(){
        //menu_icon.html 列出了所有的图标
        return new ModelAndView("sys/menu_icon");
    }

    @ApiOperation(value = "选择菜单树页"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/selectMenu")
    public ModelAndView selectMenu(ModelMap map){
        //返回菜单树集合
        map.addAttribute("menus" , menuService.getMenuTrees());
        return new ModelAndView("sys/menu_select");
    }

    @ApiOperation(value = "获取所有菜单树"  , notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/getMenuTrees")
    public List<ZySysTree> getMenuTrees(){
        return menuService.getMenuTrees();
    }

    @ApiOperation(value = "根据Id获取菜单信息"  , notes = "api接口", httpMethod = "GET")
    @GetMapping(value = "/getById")
    public ZySysMenu getById(@ApiParam(value = "菜单Id")String menuId){
        ZySysMenu menu = new ZySysMenu();
        menu.setMenuId(menuId);
        menu.setIsdel(false);
        return menuService.getOne(new QueryWrapper<>(menu));
    }

    @ApiOperation(value = "新增菜单"  , notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/add")
    public String add(@ApiParam(value = "菜单实体")@RequestBody ZySysMenu menu){
        String menuId = IdWorker.getId();
        menu.setMenuId(menuId);
        menu.setIsdel(false);
        menu.setCreateTime(LocalDateTime.now());
        menu.setCreateUserid(ContextHolder.getUserId());
        menu.setCreateUsername(ContextHolder.getUserName());
        menu.setUpdateTime(LocalDateTime.now());
        menu.setUpdateUserid(ContextHolder.getUserId());
        menu.setUpdateUsername(ContextHolder.getUserName());
        menuService.save(menu);
        return menuId;
    }

    @ApiOperation(value = "编辑菜单"  , notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/edit")
    public boolean edit(@ApiParam(value = "菜单实体")@RequestBody ZySysMenu menu){
        if(StringUtils.isEmpty(menu.getMenuId()) || StringUtils.isEmpty(menu.getMenuName())){
            throw new BusinessException("请传入菜单Id或菜单名称");
        }
        menu.setUpdateTime(LocalDateTime.now());
        menu.setUpdateUserid(ContextHolder.getUserId());
        menu.setUpdateUsername(ContextHolder.getUserName());
        return menuService.updateById(menu);
    }

    @ApiOperation(value = "删除菜单"  , notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/del")
    public boolean del(@ApiParam(value = "菜单Id",required = true)String menuId){
        if(StringUtils.isEmpty(menuId)){
            throw new BusinessException("请传入菜单Id");
        }
        ZySysMenu menu = menuService.getById(menuId);
        menu.setIsdel(true);
        return menuService.updateById(menu);
    }
}
