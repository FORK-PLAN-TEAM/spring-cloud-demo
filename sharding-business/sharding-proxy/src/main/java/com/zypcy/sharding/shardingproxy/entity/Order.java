package com.zypcy.sharding.shardingproxy.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "order_amount")
    private String orderAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "create_time")
    private Date createTime;

}