package com.liwm.seckill.mq;

import com.liwm.seckill.dto.SeckillOrderEvent;
import com.liwm.seckill.service.OrderService;
import com.liwm.seckill.service.StockService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderConsumer {

    @Autowired
    private OrderService orderService;
    @Autowired
    private StockService stockService;

    @RabbitListener(
            queues = "seckill.order.queue",
            concurrency = "5", // 并发消费者数量
            ackMode = "MANUAL"
    )
    public void handleOrderMessage(@Payload SeckillOrderEvent event,
                                   Channel channel,
                                   @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            // 1. 幂等性校验
            if (orderService.isOrderProcessed(event.getOrderId())) {
                channel.basicAck(tag, false);
                return;
            }

            // 2. 创建订单
            orderService.createOrder(event);

            // 3. 异步更新库存数据库
            stockService.asyncUpdateDBStock(event.getProductId(), event.getQuantity());

            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 重试3次后进入死信队列
            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}


