package com.liwm.sk.goods.controller;

import com.liwm.sk.common.dto.Result;
import com.liwm.sk.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/reduce-stock")
    public Result<Boolean> reduceStock(@RequestParam(value = "productId") String productId, @RequestParam(value = "num") Integer num) {
        if (goodsService.reduceStock(productId, num)) {
            return Result.success(true);
        }
        return Result.fail("库存不足");
    }

    @PostMapping("/updateDBStockk")
    public Result<Boolean> updateDBStock(@RequestParam(value = "productId") String productId, @RequestParam(value = "num") Integer num) {
        goodsService.updateDBStock(productId, num);
        return Result.success(true);
    }
}
