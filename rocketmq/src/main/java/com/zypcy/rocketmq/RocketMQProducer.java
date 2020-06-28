package com.zypcy.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author 64590 作者
 * @Time 2020-06-24 15:59
 * @Description 描述
 */
@Component
public class RocketMQProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息
     * @param topic
     * @param tags
     * @param msg
     */
    public void sendMsg(String topic , String tags, String msg) {
        this.rocketMQTemplate.convertAndSend(topic + ":" + tags, msg);
    }
}
