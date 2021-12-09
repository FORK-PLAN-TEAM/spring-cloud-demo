package com.zypcy.log.logfilter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 * ConditionalOnClass声明：只有当某个或某些类在类路径下时，才会实例化Bean
 * ConditionalOnMissingBean声明：只有在当前Spring上下文中不存在某个对象时才创建Bean，并注入到Spring上下文中
 */
@Configuration
@ConditionalOnClass({LogFilterRegistrationBean.class, LogFilter.class})
public class LogFilterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LogFilterRegistrationBean.class)
    public LogFilterRegistrationBean logFilterRegistrationBean() {
        return new LogFilterRegistrationBean();
    }

}
