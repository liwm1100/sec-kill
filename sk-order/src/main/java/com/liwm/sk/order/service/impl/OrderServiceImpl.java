package com.liwm.sk.order.service.impl;

import com.liwm.sk.common.dto.Result;
import com.liwm.sk.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Result<Void> createOrder(Long userId, Long productId, Integer count) {
        //1限流
        // 2. 用户鉴权 & 防重复提交
        String repeatKey = "seckill:user:" + userId + ":" + productId;
        if (!redisTemplate.opsForValue().setIfAbsent(repeatKey, "1", 5, TimeUnit.SECONDS)) {
            throw new BusinessException("请勿重复提交");
        }
        // 3. 原子扣减库存
        if (!stockService.deductStock(productId, 1)) {
            throw new BusinessException("库存不足");
        }
        // 4. 生成订单号并发送MQ
        String orderId = OrderIdGenerator.generate();
        orderProducer.sendSeckillOrder(new SeckillOrderEvent(orderId, productId, userId));
    }
}
