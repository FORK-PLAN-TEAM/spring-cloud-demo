package com.zypcy.sharding.shardingproxy.controller;

import com.zypcy.sharding.shardingproxy.entity.Order;
import com.zypcy.sharding.shardingproxy.repository.OrderRepository;
import com.zypcy.sharding.shardingproxy.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired private OrderRepository orderRepository;

    @RequestMapping("add")
    public Order add(){
        Order order = new Order();
        order.setOrderId(IdWorker.getLongId());
        order.setMemberId(IdWorker.getLongId());
        order.setCreateTime(new Date());
        order.setOrderAmount("331.2");
        order.setOrderCode("abc");
        order.setStatus("1");
        return orderRepository.save(order);
    }

    @GetMapping("findById/{orderId}")
    public Order findById(@PathVariable long orderId){
        return orderRepository.findById(orderId).get();
    }

    @GetMapping("findAll")
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
}
