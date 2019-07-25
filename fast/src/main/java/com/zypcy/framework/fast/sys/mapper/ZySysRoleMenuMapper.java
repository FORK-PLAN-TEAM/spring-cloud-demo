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

    @Select("select menu.menu_id as id,menu.parent_id as p_id,menu.menu_name as name,\n" +
            " (select count(menu_id) from zy_sys_role_menu rolemenu where rolemenu.role_id=#{roleId} and rolemenu.menu_id = menu.menu_id) as checked \n" +
            "from zy_sys_menu menu")
    List<ZySysTree> getRoleMenus(@Param("roleId") String roleId);
}
