package com.zypcy.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import javax.annotation.Resource;

/**
 * @Author 64590 作者
 * @Time 2020-06-24 15:39
 * @Description 描述
 */
@SpringBootTest
public class RocketMQProducerTest {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RocketMQProducer producer;

    @Test
    public void testSend() {

        //rocketMQTemplate.convertAndSend("test-topic1", "Hello, World!");

        for (int i = 0; i < 20; i++) {
            String msg = "hello world ! " + i;
            System.out.println("发送数据到" + CommonMQTopic.Mobile_Topic + ":" + CommonMQTopic.MobileTopicTag.Order + ",megssage=" + msg);
            producer.sendMsg(CommonMQTopic.Mobile_Topic, CommonMQTopic.MobileTopicTag.Order, msg);
        }
    }

    /**
     * 发送有序消息
     */
    @Test
    public void testSendOrderly() {
        for (int i = 1; i < 10; i++) {
            String message = "zhuyu " + i;
            rocketMQTemplate.asyncSendOrderly(CommonMQTopic.Mobile_Topic + ":" + CommonMQTopic.MobileTopicTag.Order, message, "" + i
                    , new SendCallback() {
                        @Override
                        public void onSuccess(SendResult sendResult) {
                            System.out.println("send success :" + sendResult);
                        }

                        @Override
                        public void onException(Throwable e) {
                            System.out.println("send exception :" + e);
                        }
                    });
        }
    }
}
