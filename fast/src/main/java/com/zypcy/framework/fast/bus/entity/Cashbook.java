package com.zypcy.framework.fast.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * 记账本
 * @author zhuyu
 * @since 2019-07-29
 */
@TableName("bus_cashbook")
@ApiModel(value = "Cashbook对象", description = "记账本")
public class Cashbook implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cash_id" , type = IdType.INPUT)
    @ApiModelProperty(value = "账目Id")
    private String cashId;

    @NotBlank(message = "账目类型不能为空")
    @ApiModelProperty(value = "账目类型，0:支出，1:收入，2:笔记")
    private String cashType;

    @NotNull(message = "记录时间不能为空")
    @ApiModelProperty(value = "记录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordTime;

    @NotBlank(message = "入账类别详情不能为空")
    @ApiModelProperty(value = "账本详情-数据字典id")
    private String dictId;

    @NotBlank(message = "入账类别详情不能为空")
    @ApiModelProperty(value = "入账类别-数据字典name")
    private String cashCategory;

    @NotNull(message = "金额不能为空")
    @ApiModelProperty(value = "记录金额")
    private Double amount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否删除")
    private Boolean isdel;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getCashCategory() {
        return cashCategory;
    }

    public void setCashCategory(String cashCategory) {
        this.cashCategory = cashCategory;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsdel() {
        return isdel;
    }

    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
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
}
