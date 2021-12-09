package com.zypcy.framework.fast.bus.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-29
 */
@TableName("bus_member")
@ApiModel(value = "Member对象", description = "")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String memberName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
