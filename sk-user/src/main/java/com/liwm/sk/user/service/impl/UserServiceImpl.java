package com.liwm.sk.user.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liwm.sk.common.dto.BusinessException;
import com.liwm.sk.user.dto.UserDTO;
import com.liwm.sk.user.mapper.UserMapper;
import com.liwm.sk.user.model.User;
import com.liwm.sk.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private static final String key = "passwordkey";

    @Override
    public String login(UserDTO userDTO) {
        Assert.notNull(userDTO,"参数为空");
        if (StringUtils.isNotBlank(userDTO.getAccount())) {
            //账号登录
            User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, userDTO.getAccount()).eq(User::getType, 2));
            Assert.notNull(user,"没有该账号");
            if (user.getState() != 1) {
                throw new BusinessException("该账号已被禁用");
            }
            if (StringUtils.equals(SaSecureUtil.aesEncrypt(key,userDTO.getPassword()),user.getPassword())) {
                StpUtil.login(user.getId());
                return StpUtil.getTokenValue();
            }
        } else if (StringUtils.isNotBlank(userDTO.getPhone())) {
            //手机号登录
            Assert.hasText(userDTO.getVerifyCode(),"请填写验证码");
            if (!StringUtils.equals("123",userDTO.getVerifyCode())) {
                throw new BusinessException("验证码不对，123");
            }
            User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getMobile, userDTO.getPhone()).eq(User::getType, 2));
            Assert.notNull(user,"没有该账号");
            if (user.getState() != 1) {
                throw new BusinessException("该账号已被禁用");
            }
            StpUtil.login(user.getId());
            return StpUtil.getTokenValue();
        } else {
            //扫码登录暂不支持
            throw new BusinessException("扫码登录暂不支持");
        }
        return null;
    }

    @Override
    public boolean register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getAccount());
        user.setNickName(userDTO.getAccount());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getPhone());
        user.setType(2);
        user.setState(1);
        user.setPasswordErrorLastTime(null);
        user.setPasswordErrorNum(0);
        user.setPasswordExpireTime(null);
        user.setPassword(SaSecureUtil.aesEncrypt(key,userDTO.getPassword()));
        user.setSalt(key);
        user.setLastLoginTime(null);
        user.setCreatedBy(null);
        user.setCreatedTime(new Date());
        user.setUpdatedBy(null);
        user.setUpdatedTime(new Date());
        return userMapper.insert(user) > 0;
    }

    @Override
    public UserDTO getCurrentUserInfo() {
        Object loginId = StpUtil.getLoginId();
        User user = userMapper.selectById(Long.parseLong(loginId+""));
        UserDTO userDTO = new UserDTO();
        userDTO.setNickName(user.getNickName());
        return userDTO;
    }
}
