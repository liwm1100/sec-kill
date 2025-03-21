package com.liwm.sk.gateway.feign.dto;

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
