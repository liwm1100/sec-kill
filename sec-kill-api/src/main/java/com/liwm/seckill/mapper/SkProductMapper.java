package com.liwm.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwm.seckill.model.SkProduct;
import org.apache.ibatis.annotations.Param;

public interface SkProductMapper extends BaseMapper<SkProduct> {
    int updateStock(@Param("productId") String productId, @Param("quantity") Integer quantity, @Param("version") Integer version);
}