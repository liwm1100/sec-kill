package com.liwm.seckill.service;

import com.liwm.seckill.mapper.SkProductMapper;
import com.liwm.seckill.model.SkProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class StockService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SkProductMapper skProductMapper;

    // Lua脚本：原子扣减库存并返回剩余量
    private static final String STOCK_DECREMENT_SCRIPT =
            "local stockKey = KEYS\n" +
            "local quantity = tonumber(ARGV)\n" +
            "local stock = tonumber(redis.call('get', stockKey))\n" +
            "if stock and stock >= quantity then\n" +
            "    redis.call('decrby', stockKey, quantity)\n" +
            "    return stock - quantity\n" +
            "else\n" +
            "    return -1\n" +
            "end";

    public boolean deductStock(String productId, int quantity) {
        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(STOCK_DECREMENT_SCRIPT, Long.class),
                Collections.singletonList("stock:" + productId),
                String.valueOf(quantity)
        );
        return result != null && result >= 0;
    }

    @Async("stockAsyncExecutor")
    public void asyncUpdateDBStock(String productId, int quantity) {
        // 1. 使用CAS乐观锁更新
        int retryCount = 0;
        while (retryCount < 3) {
            SkProduct product = skProductMapper.selectById(productId);
            if (product.getStock() >= quantity) {
                int rows = skProductMapper.updateStock(productId,quantity, product.getVersion());
                if (rows > 0) break;
            }
            retryCount++;
        }

        // 2. 失败时补偿Redis库存
        if (retryCount == 3) {
            redisTemplate.opsForValue().increment("stock:" + productId, quantity);
            log.warn("库存补偿 productId:{} quantity:{}", productId, quantity);
        }
    }

}
