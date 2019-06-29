package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 数据字典
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zy_sys_dict")
@ApiModel(value = "ZySysDict对象", description = "数据字典")
public class ZySysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id" , type = IdType.INPUT)
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

    private String remarks;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isdel;


}
