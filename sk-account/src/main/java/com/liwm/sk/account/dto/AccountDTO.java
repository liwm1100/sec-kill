package com.liwm.sk.account.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDTO implements Serializable {

    private String userId;

    private Integer price;
}
