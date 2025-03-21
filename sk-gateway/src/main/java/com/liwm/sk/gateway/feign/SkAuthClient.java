package com.liwm.sk.gateway.feign;

import com.liwm.sk.common.dto.Result;
import com.liwm.sk.gateway.feign.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "sk-auth")
public interface SkAuthClient {

    @GetMapping("/auth/permission-list")
    Result<List<String>> getPermissionList(Long userId);

    @GetMapping("/auth/role-list")
    Result<List<String>> getRoleList(Long userId);

    @GetMapping("/auth/user-info")
    Result<UserDTO> getUserInfo(Long userId);
}
