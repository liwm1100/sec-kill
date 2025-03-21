package com.liwm.sk.gateway.service.impl;

import com.liwm.sk.common.dto.BusinessException;
import com.liwm.sk.common.dto.Result;
import com.liwm.sk.common.dto.ResultEnum;
import com.liwm.sk.gateway.feign.SkAuthClient;
import com.liwm.sk.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SkAuthClient skAuthClient;


    @Override
    public List<String> getPermissionList(Long userId) {
        Result<List<String>> result = skAuthClient.getPermissionList(userId);
        if (result.getCode() != ResultEnum.SUCCESS.getCode()) {
            throw new BusinessException(ResultEnum.COMMON_FAILED.getMessage() + result.getMsg());
        }
        return result.getData();
    }

    @Override
    public List<String> getRoleList(Long userId) {
        Result<List<String>> result = skAuthClient.getRoleList(userId);
        if (result.getCode() != ResultEnum.SUCCESS.getCode()) {
            throw new BusinessException(ResultEnum.COMMON_FAILED.getMessage() + result.getMsg());
        }
        return result.getData();
    }
}
