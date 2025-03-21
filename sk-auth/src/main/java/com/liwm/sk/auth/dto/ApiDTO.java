package com.liwm.sk.auth.dto;

import lombok.Data;

@Data
public class ApiDTO {
    /**
     * 请求类型
     */
    private String requestMethod;

    /**
     * 接口地址;
     */
    private String uri;
}
