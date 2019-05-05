package com.zypcy.sharding.dbtablejpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_member")
public class Member {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "ebl_flag")
    private String eblFlag;

    @Column(name = "del_flag")
    private String delFlag;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}