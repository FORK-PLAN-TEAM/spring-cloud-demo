package com.zypcy.framework.fast.sys.dto;

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
@ApiModel(value = "ZySysRoleMenuTree对象", description = "选着的角色菜单树")
public class ZySysRoleMenuTree implements Serializable {

    @ApiModelProperty(value = "角色Id")
    private String roleId;

    @ApiModelProperty(value = "选着的菜单Id集合")
    private List<String> menuIds;

}
