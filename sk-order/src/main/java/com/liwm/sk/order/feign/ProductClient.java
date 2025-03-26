package com.liwm.sk.order.feign;

import com.liwm.sk.common.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sk-product")
public interface ProductClient {

    @PostMapping("/product/reduce-stock")
    Result<Boolean> reduceStock(@RequestParam(value = "productId") String productId, @RequestParam(value = "num") Integer num);

    @PostMapping("/product/updateDBStock")
    Result<Boolean> updateDBStock(@RequestParam(value = "productId") String productId, @RequestParam(value = "num") Integer num);
}
