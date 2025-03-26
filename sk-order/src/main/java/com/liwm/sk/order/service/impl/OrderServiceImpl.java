package com.liwm.sk.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liwm.sk.order.feign.AccountClient;
import com.liwm.sk.order.feign.ProductClient;
import com.liwm.sk.order.feign.dto.AccountDTO;
import com.liwm.sk.order.mapper.SkOrderMapper;
import com.liwm.sk.order.model.SkOrder;
import com.liwm.sk.order.mq.SeckillOrderEvent;
import com.liwm.sk.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkOrderMapper skOrderMapper;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private AccountClient accountClient;

    //幂等性校验
    public boolean isOrderProcessed(String orderId) {
        SkOrder skOrder = skOrderMapper.selectOne(Wrappers.lambdaQuery(SkOrder.class).eq(SkOrder::getOrderNum, orderId));
        return skOrder == null;
    }


    public void createOrder(SeckillOrderEvent event) {
        SkOrder skOrder = new SkOrder();
        skOrder.setOrderNum(event.getOrderId());
        skOrder.setProductId(event.getProductId());
        skOrder.setUserId(event.getUserId());
        skOrder.setQuantity(event.getNum());
        skOrderMapper.insert(skOrder);
    }

    @Override
    public void payOrder(SeckillOrderEvent event) {
        // 1. 幂等性校验
        if (this.isOrderProcessed(event.getOrderId())) {
            return;
        }
        // 2. 创建订单
        this.createOrder(event);
        // 3. 异步更新库存数据库
        productClient.updateDBStock(event.getProductId(), event.getNum());
        // 4. 扣金额
        accountClient.reduceBalance(new AccountDTO(event.getUserId(), event.getNum()));
    }
}
