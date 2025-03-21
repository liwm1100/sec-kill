package com.liwm.seckill.mq;

import com.google.protobuf.Message;
import com.liwm.seckill.config.RabbitMQConfig;
import com.liwm.seckill.dto.SeckillOrderEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSeckillOrder(SeckillOrderEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.exchange,RabbitMQConfig.key,event);
    }
}
