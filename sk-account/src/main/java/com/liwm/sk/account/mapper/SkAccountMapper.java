package com.liwm.sk.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwm.sk.account.model.SkAccount;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface SkAccountMapper extends BaseMapper<SkAccount> {
    Integer getBalance(String userId);

    int reduceBalance(@Param("userId") String userId, @Param("price") Integer price, @Param("updateTime") Timestamp updateTime);
}