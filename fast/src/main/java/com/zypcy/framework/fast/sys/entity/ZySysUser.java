package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zy_sys_user")
@ApiModel(value = "ZySysUser对象", description = "用户表")
public class ZySysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id" , type = IdType.INPUT)
    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "组织机构id")
    private String orgId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户编号")
    private String userCode;

    @ApiModelProperty(value = "登录帐号")
    private String userAccount;

    @ApiModelProperty(value = "登录密码")
    private String userPwd;

    @ApiModelProperty(value = "盐：密码加密key")
    private String salt;

    @ApiModelProperty(value = "用户性别：1女、0男")
    private Boolean sex;

    @ApiModelProperty(value = "用户状态：1禁止、0正常")
    private Boolean state;

    @ApiModelProperty(value = "是否删除")
    private Boolean isdel;

    @ApiModelProperty(value = "排序码")
    private Integer orderNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建用户Id")
    private String createUserid;

    @ApiModelProperty(value = "创建人姓名")
    private String createUsername;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改用户Id")
    private String updateUserid;

    @ApiModelProperty(value = "修改用户姓名")
    private String updateUsername;

    @ApiModelProperty(value = "最后一次登录时间")
    @TableField(exist = false)
    private long loginTime;
}
