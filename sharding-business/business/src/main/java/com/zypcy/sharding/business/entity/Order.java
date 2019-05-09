package com.zypcy.sharding.business.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private String orderId;

    private String memberId;

    private String orderCode;

    private String orderAmount;

    private String status;

    private Date createTime;

}