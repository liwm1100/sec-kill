package com.liwm.sk.order.mq;


import com.liwm.sk.order.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

    @RabbitListener(
            queues = "seckill.order.queue",
            concurrency = "5", // 并发消费者数量
            ackMode = "MANUAL"
    )
    public void handleOrderMessage(@Payload SeckillOrderEvent event,
                                   Channel channel,
                                   @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            orderService.payOrder(event);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("监听下单支付异常：" + ExceptionUtils.getFullStackTrace(e));
            // 重试3次后进入死信队列
            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}


