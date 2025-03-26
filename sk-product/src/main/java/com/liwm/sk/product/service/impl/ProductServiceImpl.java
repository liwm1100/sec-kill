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

package com.liwm.sk.product.service.impl;

import com.liwm.sk.common.dto.BusinessException;
import com.liwm.sk.product.mapper.SkProductMapper;
import com.liwm.sk.product.model.SkProduct;
import com.liwm.sk.product.service.ProductService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * @author TrevorLink
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {


	@Autowired
	private SkProductMapper skProductMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;

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

	@Override
	@Transactional
	public boolean reduceStock(String productId, Integer num) {
		log.info("[reduceBalance] currenet XID: {}", RootContext.getXID());
		Long result = redisTemplate.execute(
				new DefaultRedisScript<>(STOCK_DECREMENT_SCRIPT, Long.class),
				Collections.singletonList("stock:" + productId),
				String.valueOf(num)
		);
		return result != null && result >= 0;
	}

	@Override
	public void updateDBStock(String productId, int num) {
		// 1. 使用CAS乐观锁更新
		int retryCount = 0;
		while (retryCount < 3) {
			SkProduct product = skProductMapper.selectById(productId);
			if (product.getStock() >= num) {
				int rows = skProductMapper.updateStock(productId,num, product.getVersion());
				if (rows > 0) break;
			}
			retryCount++;
		}
		// 2. 失败时
		if (retryCount == 3) {
			throw new BusinessException("更新db库存异常，影响行数=0");
		}
	}
}
