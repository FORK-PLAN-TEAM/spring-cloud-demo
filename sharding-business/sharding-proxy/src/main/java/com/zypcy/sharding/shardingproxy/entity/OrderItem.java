package com.zypcy.sharding.shardingproxy.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_order_item")
public class OrderItem {

    @Id
    @Column(name = "item_id")
    private Long itemId;

    private Long orderId;

    private String productName;

    private Double itemAccount;

    private Date createTime;
}