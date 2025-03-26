package com.liwm.sk.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwm.sk.goods.model.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper extends BaseMapper<Product> {

    int updateStock(@Param("productId") String productId, @Param("num") Integer num, @Param("version") Integer version);
}