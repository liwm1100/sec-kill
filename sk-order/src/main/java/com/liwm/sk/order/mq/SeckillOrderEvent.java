package com.liwm.sk.order.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrderEvent {
    private String userId;
    private String productId;
    private String orderId;
    private Integer num;
}
