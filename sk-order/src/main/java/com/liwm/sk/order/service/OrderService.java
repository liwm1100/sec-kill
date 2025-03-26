package com.liwm.sk.order.service;

import com.liwm.sk.order.mq.SeckillOrderEvent;

public interface OrderService {

    void payOrder(SeckillOrderEvent event);
}
