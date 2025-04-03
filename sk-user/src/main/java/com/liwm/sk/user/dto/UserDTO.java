package com.liwm.sk.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private String nickName;
    private String phone;
    private String email;
    private String account;
    private String password;
    private String verifyCode;
    private String checked;
}
