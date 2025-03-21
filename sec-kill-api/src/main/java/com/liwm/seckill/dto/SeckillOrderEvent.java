package com.liwm.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrderEvent implements Serializable {

    private String orderId;
    private String productId;
    private String userId;
    private Integer quantity;
}
