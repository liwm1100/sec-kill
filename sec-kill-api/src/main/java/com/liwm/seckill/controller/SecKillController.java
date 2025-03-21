package com.liwm.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.liwm.seckill.annotations.RateLimit;
import com.liwm.seckill.dto.BusinessException;
import com.liwm.seckill.dto.Result;
import com.liwm.seckill.dto.SeckillOrderEvent;
import com.liwm.seckill.mq.OrderProducer;
import com.liwm.seckill.service.StockService;
import com.liwm.seckill.util.OrderIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/seckill")
public class SecKillController {

    @Autowired
    private StockService stockService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OrderProducer orderProducer;

    // 1. 令牌桶限流
    @RateLimit(permitsPerSecond = 2,key = "stock:product")
    @PostMapping("/{productId}")
    public Result<String> seckill(@PathVariable String productId,HttpServletRequest request) {
        // 2. 用户鉴权 & 防重复提交
        String userId = request.getParameter("userId");
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
        return Result.success(orderId);
    }
}
