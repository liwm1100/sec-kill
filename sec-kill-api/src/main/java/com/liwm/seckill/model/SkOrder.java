package com.liwm.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sk_order")
public class SkOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "order_num")
    private String orderNum;

    @TableField(value = "product_id")
    private String productId;

    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "quantity")
    private Integer quantity;
}