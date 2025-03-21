package com.liwm.sk.order.service;

import com.liwm.sk.common.dto.Result;

public interface OrderService {
    Result<Void> createOrder(Long userId, Long productId, Integer count);
}
