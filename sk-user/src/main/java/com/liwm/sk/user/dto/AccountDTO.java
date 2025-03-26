package com.liwm.sk.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountDTO implements Serializable {

    private Long userId;

    private BigDecimal money;
}
