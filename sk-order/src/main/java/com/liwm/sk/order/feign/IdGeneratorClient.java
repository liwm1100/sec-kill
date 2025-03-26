package com.liwm.sk.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "id-generator-server", url="http://localhost:8080")
public interface IdGeneratorClient {

    @GetMapping("/api/segment/get/{key}")
    String getSegmentId(@PathVariable("key") String key);
}
