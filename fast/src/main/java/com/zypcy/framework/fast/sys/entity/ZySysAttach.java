package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 附件表，同一用户上传多个附件请用attach_no
 * @author zhuyu
 * @since 2019-07-31
 */
@TableName("zy_sys_attach")
@ApiModel(value = "ZySysAttach对象", description = "附件表，同一用户上传多个附件请用attach_no")
public class ZySysAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    private String attachId;

    @ApiModelProperty(value = "上传多个附件用attach_no标识")
    private String attachNo;

    @ApiModelProperty(value = "文件名")
    private String attachName;

    @ApiModelProperty(value = "文件旧名称")
    private String attachOldName;

    @ApiModelProperty(value = "文件大小")
    private Long attachSize;

    @ApiModelProperty(value = "附件路径")
    private String attachPath;

    @ApiModelProperty(value = "附件后缀")
    private String attachSuffix;

    @ApiModelProperty(value = "文件contentType，下载相关")
    @TableField("contentType")
    private String contentType;

    @ApiModelProperty(value = "md5值，实现秒传功能")
    private String md5;

    @ApiModelProperty(value = "是否删除，0未删除，1已删除")
    private Boolean isdel;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "附件描述，可做扩展备用字段")
    private String description;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getAttachNo() {
        return attachNo;
    }

    public void setAttachNo(String attachNo) {
        this.attachNo = attachNo;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getAttachOldName() {
        return attachOldName;
    }

    public void setAttachOldName(String attachOldName) {
        this.attachOldName = attachOldName;
    }

    public Long getAttachSize() {
        return attachSize;
    }

    public void setAttachSize(Long attachSize) {
        this.attachSize = attachSize;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

    public String getAttachSuffix() {
        return attachSuffix;
    }

    public void setAttachSuffix(String attachSuffix) {
        this.attachSuffix = attachSuffix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
