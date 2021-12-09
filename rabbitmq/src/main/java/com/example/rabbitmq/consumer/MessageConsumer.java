package com.example.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @description:
 * @author: zhuyu
 * @date: 2021/2/26 21:16
 */
@Component
@RabbitListener(queues = "")
public class MessageConsumer {
}
