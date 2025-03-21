package com.liwm.sk.auth.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwm.sk.auth.model.BaseApplication;
import com.liwm.sk.auth.mapper.BaseApplicationMapper;
import com.liwm.sk.auth.service.BaseApplicationService;
@Service
public class BaseApplicationServiceImpl extends ServiceImpl<BaseApplicationMapper, BaseApplication> implements BaseApplicationService{

}
