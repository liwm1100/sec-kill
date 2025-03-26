package com.liwm.sk.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwm.sk.product.model.SkProduct;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface SkProductMapper extends BaseMapper<SkProduct> {
    int updateStock(@Param("productId") String productId, @Param("num") Integer num, @Param("version") Integer version);
}