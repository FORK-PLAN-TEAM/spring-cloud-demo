package com.zypcy.sharding.sublibrarytable.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -1205226416664488559L;

    private Long orderId;

    private Long memberId;

    private String orderCode;

    private Double orderAmount;

    private String status;

    private Date createTime;
}