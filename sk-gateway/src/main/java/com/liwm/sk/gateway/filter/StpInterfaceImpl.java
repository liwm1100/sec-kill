package com.liwm.sk.gateway.filter;

import cn.dev33.satoken.stp.StpInterface;
import com.liwm.sk.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private AuthService authService;

    @Override
    public List<String> getPermissionList(Object userId, String loginType) {
        return authService.getPermissionList(Long.parseLong(userId+""));
    }

    @Override
    public List<String> getRoleList(Object userId, String loginType) {
        return authService.getRoleList(Long.parseLong(userId+""));
    }
}
