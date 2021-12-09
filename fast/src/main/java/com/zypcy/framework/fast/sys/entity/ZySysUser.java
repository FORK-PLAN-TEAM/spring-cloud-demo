package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@TableName("zy_sys_user")
@ApiModel(value = "ZySysUser对象", description = "用户表")
public class ZySysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "组织机构id")
    private String orgId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户编号")
    private String userCode;

    @ApiModelProperty(value = "手机号，登录帐号")
    private String cellPhone;

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

    @ApiModelProperty(value = "登录平台：Pc、Wx、App")
    @TableField(exist = false)
    private String loginPlatform;

    @ApiModelProperty(value = "用户拥有的角色Ids集合")
    @TableField(exist = false)
    private String roleIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getIsdel() {
        return isdel;
    }

    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(String updateUserid) {
        this.updateUserid = updateUserid;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginPlatform() {
        return loginPlatform;
    }

    public void setLoginPlatform(String loginPlatform) {
        this.loginPlatform = loginPlatform;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
}
