package com.liwm.sk.auth.service.impl;

import com.liwm.sk.auth.vo.ResourcesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwm.sk.auth.mapper.BaseResourceMapper;
import com.liwm.sk.auth.model.BaseResource;
import com.liwm.sk.auth.service.BaseResourceService;

import java.util.Collections;
import java.util.List;

@Service
public class BaseResourceServiceImpl extends ServiceImpl<BaseResourceMapper, BaseResource> implements BaseResourceService{

    @Autowired
    private BaseResourceMapper baseResourceMapper;

    @Override
    public List<ResourcesVo> listAllResourceApi() {
        return baseResourceMapper.listAllResourceApi();
    }
}
