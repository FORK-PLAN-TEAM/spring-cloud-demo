package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色菜单
 * @author zhuyu
 * @since 2019-06-14
 */
@TableName("zy_sys_role_menu")
@ApiModel(value = "ZySysRoleMenu对象", description = "角色菜单")
public class ZySysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id" , type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "菜单Id")
    private String menuId;

    @ApiModelProperty(value = "角色Id")
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
