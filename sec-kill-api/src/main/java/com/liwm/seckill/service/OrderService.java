package com.liwm.seckill.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liwm.seckill.dto.SeckillOrderEvent;
import com.liwm.seckill.mapper.SkOrderMapper;
import com.liwm.seckill.model.SkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private SkOrderMapper skOrderMapper;

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
        skOrder.setQuantity(event.getQuantity());
        skOrderMapper.insert(skOrder);
    }
}
