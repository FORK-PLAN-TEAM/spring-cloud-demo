package com.zypcy.framework.fast.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "登录信息对象")
public class ZySysLoginInfo implements Serializable{

    @ApiModelProperty(value = "用户信息")
    private ZySysUser sysUser;

    @ApiModelProperty(value = "用户角色信息")
    private List<ZySysRole> userRoles;

}
