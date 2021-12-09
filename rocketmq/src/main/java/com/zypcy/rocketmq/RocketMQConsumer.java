package com.zypcy.rocketmq;

/**
 * 接收队列消息
 *
 * @Author 64590 作者
 * @Time 2020-06-24 15:41
 * @Description 描述
 */

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        consumerGroup = "${rocketmq.producer.group}",
        topic = CommonMQTopic.Mobile_Topic,                      //topic
        selectorExpression = CommonMQTopic.MobileTopicTag.Order, //tag
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING,
        consumeThreadMax = 1
)
public class RocketMQConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("接收到消息 -> " + message);
    }
}
