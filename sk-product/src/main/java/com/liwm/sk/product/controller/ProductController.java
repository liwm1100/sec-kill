package com.liwm.sk.product.controller;

import com.liwm.sk.common.dto.Result;
import com.liwm.sk.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/reduce-stock")
    public Result<Boolean> reduceStock(@RequestParam(value = "productId") String productId, @RequestParam(value = "num") Integer num) {
        if (productService.reduceStock(productId, num)) {
            return Result.success(true);
        }
        return Result.fail("库存不足");
    }

    @PostMapping("/updateDBStockk")
    public Result<Boolean> updateDBStock(@RequestParam(value = "productId") String productId, @RequestParam(value = "num") Integer num) {
        productService.updateDBStock(productId, num);
        return Result.success(true);
    }
}
