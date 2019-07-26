package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface ZySysMenuMapper extends BaseMapper<ZySysMenu> {

    //获取所有状态正常，且未删除的菜单
    List<ZySysTree> getMenuTrees();

    //获取用户拥有的角色菜单
    List<ZySysMenu> getMenusByRoleId(List<String> roleIds);

    //超级管理员获取所有菜单
    List<ZySysMenu> getMenusByAdmin();
}
