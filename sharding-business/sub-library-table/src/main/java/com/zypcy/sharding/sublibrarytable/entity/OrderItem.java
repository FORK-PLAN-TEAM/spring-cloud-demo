package com.zypcy.sharding.sublibrarytable.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderItem {
    private Long itemId;

    private Long orderId;

    private String productName;

    private Double itemAccount;

    private Date createTime;
}