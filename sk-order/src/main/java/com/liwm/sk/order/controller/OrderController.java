/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liwm.sk.order.controller;

import com.liwm.sk.common.dto.BusinessException;
import com.liwm.sk.common.dto.Result;
import com.liwm.sk.common.dto.ResultEnum;
import com.liwm.sk.order.feign.IdGeneratorClient;
import com.liwm.sk.order.feign.ProductClient;
import com.liwm.sk.order.mq.OrderProducer;
import com.liwm.sk.order.mq.SeckillOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author TrevorLink
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private IdGeneratorClient idGeneratorClient;

	@PostMapping("/create")
	public Result<?> createOrder(@RequestParam("userId") String userId,
								 @RequestParam("productId") String productId,
								 @RequestParam("count") Integer count) {
        // 1.防重复提交
        String repeatKey = "sk:user:" + userId + ":" + productId;
        if (!redisTemplate.opsForValue().setIfAbsent(repeatKey, "1", 5, TimeUnit.SECONDS)) {
            throw new BusinessException("请勿重复提交");
        }
        // 2. 原子扣减库存
        Result<Boolean> result = productClient.reduceStock(productId + "", count);
        if (result.getCode() == ResultEnum.COMMON_FAILED.getCode()) {
            throw new BusinessException(result.getMsg());
        }
        // 3. 生成订单号并发送MQ
        String orderId = idGeneratorClient.getSegmentId("order-id");
        orderProducer.sendSeckillOrder(new SeckillOrderEvent(userId,productId,orderId,count));
        return Result.success(orderId);
	}

}
