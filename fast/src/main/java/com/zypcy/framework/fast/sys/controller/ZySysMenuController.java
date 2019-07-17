package com.zypcy.framework.fast.sys.controller;


import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.sys.service.IZySysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@RestController
@RequestMapping("/sys/menu")
public class ZySysMenuController {

    @Autowired private IZySysMenuService menuService;

    @ApiOperation(value = "菜单列表页"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ModelAndView list(ModelMap map){
        map.addAttribute("menus" , menuService.getMenuTrees());
        return new ModelAndView("sys/menu_list");
    }

    @ApiOperation(value = "列表数据"  , notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/tree")
    public ResponseModel tree(){
        return null;
    }


}
