package com.liwm.sk.account.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

@Data
@TableName(value = "sk_account")
public class SkAccount {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "money")
    private BigDecimal money;
}