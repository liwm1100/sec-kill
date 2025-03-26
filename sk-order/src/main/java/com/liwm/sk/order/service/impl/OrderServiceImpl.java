package com.liwm.sk.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liwm.sk.order.feign.AccountClient;
import com.liwm.sk.order.feign.ProductClient;
import com.liwm.sk.order.feign.dto.AccountDTO;
import com.liwm.sk.order.mapper.OrderMapper;
import com.liwm.sk.order.model.Order;
import com.liwm.sk.order.mq.SeckillOrderEvent;
import com.liwm.sk.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private AccountClient accountClient;

    //幂等性校验
    public boolean isOrderProcessed(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        return order == null;
    }

    public void createOrder(SeckillOrderEvent event) {
        Order order = new Order();
        order.setId(Long.parseLong(event.getOrderId()));
        order.setProductId(Long.parseLong(event.getProductId()));
        order.setUserId(Long.parseLong(event.getUserId()));
        order.setNum(event.getNum());
        order.setMoney(new BigDecimal(event.getNum()));
        orderMapper.insert(order);
    }

    @Override
    public void payOrder(SeckillOrderEvent event) {
        // 1. 幂等性校验
        if (!this.isOrderProcessed(Long.parseLong(event.getOrderId()))) {
            return;
        }
        // 2. 创建订单
        this.createOrder(event);
        // 3. 更新库存数据库
        productClient.updateDBStock(event.getProductId(), event.getNum());
        // 4. 扣金额
        accountClient.reduceBalance(new AccountDTO(Long.parseLong(event.getUserId()), new BigDecimal(event.getNum())));
    }
}
