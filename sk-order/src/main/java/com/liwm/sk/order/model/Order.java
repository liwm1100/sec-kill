package com.liwm.sk.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

@Data
@TableName(value = "`order`")
public class Order {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField(value = "product_id")
    private Long productId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "num")
    private Integer num;

    @TableField(value = "money")
    private BigDecimal money;
}