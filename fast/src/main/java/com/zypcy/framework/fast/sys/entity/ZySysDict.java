package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据字典
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@TableName("zy_sys_dict")
@ApiModel(value = "ZySysDict对象", description = "数据字典")
public class ZySysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "数据字典id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "key")
    private String name;

    @ApiModelProperty(value = "value")
    private String value;

    @ApiModelProperty(value = "类型，同一类型数据字典类型一样")
    private String type;

    @ApiModelProperty(value = "sort")
    private Integer sort;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isdel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsdel() {
        return isdel;
    }

    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }
}
