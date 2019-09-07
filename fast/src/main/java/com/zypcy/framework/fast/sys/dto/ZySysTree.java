package com.zypcy.framework.fast.sys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getExtendData() {
        return extendData;
    }

    public void setExtendData(String extendData) {
        this.extendData = extendData;
    }
}
