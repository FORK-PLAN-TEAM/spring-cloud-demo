package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface ZySysRoleMenuMapper extends BaseMapper<ZySysRoleMenu> {

    //当前角色查询角色内的菜单
    @Select("select menu.menu_id as id,menu.parent_id as p_id,menu.menu_name as name,\n" +
            " (select count(menu_id) from zy_sys_role_menu rolemenu where rolemenu.role_id=#{roleId} and rolemenu.menu_id = menu.menu_id) as checked\n" +
            "from zy_sys_menu menu \n" +
            "inner join zy_sys_role_menu rolemenu on rolemenu.menu_id=menu.menu_id and rolemenu.role_id in('641521417703686144','641523765272412160')\n" +
            "where menu.state=0 and menu.isdel=0\n" +
            "group by menu.menu_id order by menu.order_num asc")
    List<ZySysTree> getRoleMenus(@Param("currentRoleIds") String currentRoleIds, @Param("roleId") String roleId);

    //超级管理员查询所有菜单
    @Select("select menu.menu_id as id,menu.parent_id as p_id,menu.menu_name as name,\n" +
            " (select count(menu_id) from zy_sys_role_menu rolemenu where rolemenu.role_id=#{roleId} and rolemenu.menu_id = menu.menu_id) as checked \n" +
            "from zy_sys_menu menu where menu.state=0 and menu.isdel=0 order by menu.order_num asc")
    List<ZySysTree> getAdminRoleMenus(@Param("roleId") String roleId);
}
