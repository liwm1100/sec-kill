package com.liwm.sk.auth.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwm.sk.auth.mapper.BaseUserMapper;
import com.liwm.sk.auth.model.BaseUser;
import com.liwm.sk.auth.service.BaseUserService;
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements BaseUserService{

}
