package com.zypcy.framework.fast.bus.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 记账本添加、编辑DTO
 */
public class CashbookSaveDto {

    @TableId(value = "cash_id", type = IdType.INPUT)
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

    @NotBlank(message = "入账类别不能为空")
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

    @Override
    public String toString() {
        return "CashbookSaveDto{" +
                "cashId='" + cashId + '\'' +
                ", cashType='" + cashType + '\'' +
                ", recordTime=" + recordTime +
                ", dictId='" + dictId + '\'' +
                ", cashCategory='" + cashCategory + '\'' +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                ", isdel=" + isdel +
                '}';
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
}
