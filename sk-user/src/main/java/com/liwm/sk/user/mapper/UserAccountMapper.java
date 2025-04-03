package com.liwm.sk.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwm.sk.user.model.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface UserAccountMapper extends BaseMapper<UserAccount> {

    int reduceBalance(@Param("userId") Long userId, @Param("price") BigDecimal price, @Param("updateTime") Timestamp updateTime);
}