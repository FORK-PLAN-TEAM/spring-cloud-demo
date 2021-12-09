package com.zypcy.sharding.shardingproxy.repository;

import com.zypcy.sharding.shardingproxy.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
