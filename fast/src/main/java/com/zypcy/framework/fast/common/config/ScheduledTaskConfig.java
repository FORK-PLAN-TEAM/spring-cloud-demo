package com.zypcy.framework.fast.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Scheduled 定时任务配置类，配置4个线程处理所有的定时任务
 */
@Configuration
@EnableScheduling
public class ScheduledTaskConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskSchedulingExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskSchedulingExecutor() {
        //指定线程池大小，可根据定时任务多少进行配置，这样的话，定时任务之间就不会相互影响
        return Executors.newScheduledThreadPool(4);
    }
}