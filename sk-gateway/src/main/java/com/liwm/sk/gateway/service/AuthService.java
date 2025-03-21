package com.liwm.sk.gateway.service;

import com.liwm.sk.common.dto.Result;

import java.util.List;

public interface AuthService {

    List<String> getPermissionList(Long userId);

    List<String> getRoleList(Long userId);

}
