package com.zypcy.framework.fast.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "ZySysRoleMenuTree对象", description = "选着的角色菜单树")
public class ZySysRoleMenuTree implements Serializable {

    @ApiModelProperty(value = "角色Id")
    private String roleId;

    @ApiModelProperty(value = "选着的菜单Id集合")
    private List<String> menuIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }
}
