package com.liwm.sk.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwm.sk.user.model.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface UserAccountMapper extends BaseMapper<UserAccount> {

    int reduceBalance(@Param("userId") String userId, @Param("price") Integer price, @Param("updateTime") Timestamp updateTime);
}