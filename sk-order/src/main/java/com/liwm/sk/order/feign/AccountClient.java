package com.liwm.sk.order.feign;

import com.liwm.sk.common.dto.Result;
import com.liwm.sk.order.feign.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("sk-account")
public interface AccountClient {

    @PostMapping("/reduce-balance")
     Result<?> reduceBalance(@RequestBody AccountDTO accountDTO);
}
