package com.zypcy.sharding.sublibrarytable.service.impl;

import com.zypcy.sharding.sublibrarytable.entity.Order;
import com.zypcy.sharding.sublibrarytable.repository.OrderMapper;
import com.zypcy.sharding.sublibrarytable.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int add(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Override
    public Order findById(String orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }
}
