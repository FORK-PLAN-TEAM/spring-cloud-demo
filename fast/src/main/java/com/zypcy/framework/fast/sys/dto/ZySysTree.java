package com.zypcy.framework.fast.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ZySysTree对象", description = "树对象")
public class ZySysTree implements Serializable{


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "pId")
    @JsonProperty("pId") //指定json序列化时的值
    private String pId;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "iconSkin,图标样式")
    private String iconSkin;

    @ApiModelProperty(value = "是否选中")
    private boolean checked;

    @ApiModelProperty(value = "是否有上级")
    private boolean isParent;

    @ApiModelProperty(value = "存放扩展数据")
    private String extendData;
}
