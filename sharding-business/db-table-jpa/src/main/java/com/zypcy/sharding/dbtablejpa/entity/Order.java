package com.zypcy.sharding.dbtablejpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    private String memberId;

    private String orderCode;

    private Double orderAmount;

    private String status;

    private Date createTime;

}