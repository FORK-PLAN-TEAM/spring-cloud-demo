package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
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


}
