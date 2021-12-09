package com.zypcy.framework.fast.sys.controller;

import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.sys.entity.ZySysMenu;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.service.IZySysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "sys-后台主页")
@RestController
@RequestMapping("sys/main")
public class ZySysMainController {

    @Autowired
    IZySysMenuService menuService;

    @ApiOperation(value = "登录成功后返回系统后台主页面", notes = "页面", httpMethod = "GET")
    @GetMapping()
    public ModelAndView main(ModelMap map) {
        //获取当前人所拥有的角色菜单 , 如果是admin则获取所有菜单
        String menuHtml = getUserMenuHtml();
        map.addAttribute("menus", menuHtml);
        map.addAttribute("currentUserName", ContextHolder.getUserName());
        return new ModelAndView("sys/main");
    }

    @ApiOperation(value = "系统后台桌面页面", notes = "页面", httpMethod = "GET")
    @GetMapping("desktop")
    public ModelAndView desktop() {
        return new ModelAndView("sys/desktop");
    }

    /**
     * 获取当前人拥有的角色菜单
     *
     * @return
     */
    private String getUserMenuHtml() {
        List<ZySysMenu> menus = new ArrayList<>();
        if ("admin".equals(ContextHolder.getUserId())) {
            //如果是超级管理员，则获取所有菜单
            menus = menuService.getMenusByAdmin();
        } else {
            //获取当前人所拥有的角色菜单
            List<ZySysRole> userRoles = ContextHolder.getSysUserRoles();
            if (userRoles.size() > 0) {
                List<String> roleIds = userRoles.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
                //根据角色Id获取菜单信息
                menus = menuService.getMenusByRoleId(roleIds);
            }
        }

        StringBuffer sb = new StringBuffer();
        //只生成三级菜单，根目录的parentId=0
        List<ZySysMenu> firstMenus = getMenuByParentId(menus, "0");
        for (ZySysMenu menu : firstMenus) {
            sb.append("<li>");
            sb.append("<a href=\"javascript:;\" " + getPath(menu.getPath()) + " >\n" +
                    "                        <i class=\"iconfont left-nav-li\" lay-tips=\"" + menu.getMenuName() + "\">" + getIcon(menu.getIcon()) + "</i>\n" +
                    "                        <cite>" + menu.getMenuName() + "</cite>\n" +
                    "                        <i class=\"iconfont nav_right\">&#xe697;</i></a>");

            //生成二级菜单
            List<ZySysMenu> twoMenus = getMenuByParentId(menus, menu.getMenuId());
            if (twoMenus.size() > 0) {
                sb.append("<ul class=\"sub-menu\">");
                for (ZySysMenu twoMenu : twoMenus) {
                    sb.append("<li>");
                    sb.append("<a href=\"javascript:;\" " + getPath(twoMenu.getPath()) + " >\n" +
                            "                        <i class=\"iconfont left-nav-li\" lay-tips=\"" + twoMenu.getMenuName() + "\">" + getIcon(twoMenu.getIcon()) + "</i>\n" +
                            "                        <cite>" + twoMenu.getMenuName() + "</cite>");

                    //生成三级菜单右侧图标
                    List<ZySysMenu> threeMenus = getMenuByParentId(menus, twoMenu.getMenuId());
                    if (threeMenus.size() > 0) {
                        sb.append("<i class=\"iconfont nav_right\">&#xe697;</i>");
                    }
                    sb.append("</a>");
                    //生成三级菜单
                    if (threeMenus.size() > 0) {
                        sb.append("<ul class=\"sub-menu\">");
                        for (ZySysMenu threeMenu : threeMenus) {
                            sb.append("<li>");
                            sb.append("<a href=\"javascript:;\" " + getPath(threeMenu.getPath()) + " >\n" +
                                    "                        <i class=\"iconfont left-nav-li\" lay-tips=\"" + threeMenu.getMenuName() + "\">" + getIcon(threeMenu.getIcon()) + "</i>\n" +
                                    "                        <cite>" + threeMenu.getMenuName() + "</cite></a>");
                            sb.append("</li>");
                        }
                        sb.append("</ul>");
                    }
                    sb.append("</li>");
                }
                sb.append("</ul>");
            }
            sb.append("</li>");
        }
        return sb.toString();
    }

    /**
     * 根据ParentId获取菜单
     *
     * @param menus
     * @param parentId
     * @return
     */
    private List<ZySysMenu> getMenuByParentId(List<ZySysMenu> menus, String parentId) {
        return menus.stream().filter(item -> parentId.equals(item.getParentId())).collect(Collectors.toList());
    }

    private String getPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        } else {
            return "_href=\"" + path + "\"";
        }
    }

    private String getIcon(String icon) {
        if (StringUtils.isEmpty(icon)) {
            return "&#xe723;";
        } else {
            return icon;
        }
    }
}
