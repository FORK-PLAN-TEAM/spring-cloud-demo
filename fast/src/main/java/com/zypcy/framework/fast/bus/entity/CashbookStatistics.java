package com.zypcy.framework.fast.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 定时任务统计用户不同月份-不同类型-不同类别数据
 *
 * @author zhuyu
 * @since 2019-10-09
 */
@TableName("bus_cashbook_statistics")
@ApiModel(value = "CashbookStatistics对象", description = "定时任务统计记账本数据")
public class CashbookStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sid", type = IdType.INPUT)
    @ApiModelProperty(value = "统计Id")
    private String sid;

    @ApiModelProperty(value = "用户Id")
    private String createUserid;

    @ApiModelProperty(value = "年份")
    private Integer syear;

    @ApiModelProperty(value = "月份")
    private Integer smonth;

    @ApiModelProperty(value = "账目类型，0:支出，1:收入")
    private String cashType;

    @ApiModelProperty(value = "账本类别-数据字典id")
    private String dictId;

    @ApiModelProperty(value = "账本类别-数据字典dict_id的value值")
    private String cashCategory;

    @ApiModelProperty(value = "总额")
    private Double amount;

    @ApiModelProperty(value = "扩展字段，收入金额，不属于数据库")
    public Double extendAmount;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public Integer getSyear() {
        return syear;
    }

    public void setSyear(Integer syear) {
        this.syear = syear;
    }

    public Integer getSmonth() {
        return smonth;
    }

    public void setSmonth(Integer smonth) {
        this.smonth = smonth;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
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

    public Double getExtendAmount() {
        return extendAmount;
    }

    public void setExtendAmount(Double extendAmount) {
        this.extendAmount = extendAmount;
    }
}
