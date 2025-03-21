package com.liwm.sk.auth.dto;

import lombok.Data;

@Data
public class ResourcesDTO {
    private String uri;
    private String requestMethod;
    private String code;
}
