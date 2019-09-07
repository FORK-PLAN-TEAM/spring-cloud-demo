package com.zypcy.framework.fast.sys.dto;

import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "登录信息对象")
public class ZySysLoginInfo implements Serializable{

    @ApiModelProperty(value = "用户信息")
    private ZySysUser sysUser;

    @ApiModelProperty(value = "用户角色信息")
    private List<ZySysRole> userRoles;

    public ZySysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(ZySysUser sysUser) {
        this.sysUser = sysUser;
    }

    public List<ZySysRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<ZySysRole> userRoles) {
        this.userRoles = userRoles;
    }
}
