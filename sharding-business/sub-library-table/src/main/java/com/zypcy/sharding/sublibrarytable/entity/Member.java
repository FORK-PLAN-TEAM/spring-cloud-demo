package com.zypcy.sharding.sublibrarytable.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Member {
    private Long memberId;

    private String memberName;

    private String nickName;

    private String accountNo;

    private String password;

    private Integer age;

    private Date birthDate;

    private String eblFlag;

    private String delFlag;

    private String description;

    private Date createTime;

    private Date updateTime;
}