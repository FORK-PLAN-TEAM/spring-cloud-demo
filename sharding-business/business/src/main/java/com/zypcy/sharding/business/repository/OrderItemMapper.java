package com.zypcy.sharding.business.repository;

import com.zypcy.sharding.business.entity.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(String itemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(String itemId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}