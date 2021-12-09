package com.zypcy.springcloud.ribbonservice;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ribbon负载均衡配置
 */
@Configuration
public class RibbonLBConfig {

    //使用随机策略
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}
