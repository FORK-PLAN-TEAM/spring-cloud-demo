package com.zypcy.framework.fast.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 附件表，同一用户上传多个附件请用attach_no
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
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


}
