package com.liwm.sk.gateway.feign.dto;

import lombok.Data;

@Data
public class ResourcesDTO {
    private String uri;
    private String requestMethod;
    private String code;
}
