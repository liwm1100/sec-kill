package com.liwm.sk.order.mq;


import com.liwm.sk.order.config.RabbitMQConfig;
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
