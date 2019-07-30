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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 记账本
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_cashbook")
@ApiModel(value = "Cashbook对象", description = "记账本")
public class Cashbook implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cash_id" , type = IdType.INPUT)
    @ApiModelProperty(value = "账目Id")
    private String cashId;

    @ApiModelProperty(value = "记录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordTime;

    @ApiModelProperty(value = "数据字典id")
    private String dictId;

    @ApiModelProperty(value = "账本详情-数据字典name")
    private String cashDetail;

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


}
