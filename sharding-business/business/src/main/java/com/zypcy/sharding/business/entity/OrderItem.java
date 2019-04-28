package com.zypcy.sharding.business.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderItem {
    private String itemId;

    private String orderId;

    private String productName;

    private Double itemAccount;

    private Date createTime;

}