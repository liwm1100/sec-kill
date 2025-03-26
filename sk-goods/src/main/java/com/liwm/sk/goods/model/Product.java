package com.liwm.sk.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "product")
public class Product {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "product_name")
    private String productName;

    @TableField(value = "stock")
    private Integer stock;

    @TableField(value = "version")
    private Integer version;
}