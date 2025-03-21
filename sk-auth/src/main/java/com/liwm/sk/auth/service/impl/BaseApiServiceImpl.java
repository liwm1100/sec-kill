package com.liwm.sk.auth.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwm.sk.auth.mapper.BaseApiMapper;
import com.liwm.sk.auth.model.BaseApi;
import com.liwm.sk.auth.service.BaseApiService;
@Service
public class BaseApiServiceImpl extends ServiceImpl<BaseApiMapper, BaseApi> implements BaseApiService{

}
