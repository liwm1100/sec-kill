package com.liwm.sk.auth;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ip白名单
 * 身份认证
 * 权限认证
 */
@MapperScan(basePackages = "com.liwm.sk.auth.mapper")
@SpringBootApplication
public class SkAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkAuthApplication.class, args);
    }

}
