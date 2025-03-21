package com.liwm.sk.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.liwm.sk.account.mapper")
@SpringBootApplication
public class SkAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkAccountApplication.class, args);
    }

}
