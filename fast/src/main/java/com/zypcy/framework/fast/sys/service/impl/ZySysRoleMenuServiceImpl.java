package com.zypcy.framework.fast.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.entity.ZySysRoleMenu;
import com.zypcy.framework.fast.sys.mapper.ZySysRoleMenuMapper;
import com.zypcy.framework.fast.sys.service.IZySysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        //如果是超级管理员，可以看所有菜单
        if("admin".equals(ContextHolder.getUserId())){
            return roleMenuMapper.getAdminRoleMenus(roleId);
        }else{
            //返回自己角色内的菜单
            String currentRoleIds = ContextHolder.getSysUserRoles().stream().map( role -> role.getRoleId()).collect(Collectors.joining(","));
            /*List<ZySysRole> roles = ContextHolder.getSysUserRoles();
            for(int i=0; i < roles.size() ; i++){
                if(i > 0){
                    currentRoleIds += ",";
                }
                currentRoleIds += "'"+ roles.get(i).getRoleId() + "'";
            }*/
            return roleMenuMapper.getRoleMenus(currentRoleIds ,roleId);
        }
    }

}
