package com.zypcy.sharding.sublibrarytable.service;

import com.zypcy.sharding.sublibrarytable.entity.Order;

public interface IOrderService {

    int add(Order order);

    Order findById(String orderId);
}
