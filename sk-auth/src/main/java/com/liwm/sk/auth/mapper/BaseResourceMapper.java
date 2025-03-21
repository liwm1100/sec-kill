package com.liwm.sk.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwm.sk.auth.model.BaseResource;
import com.liwm.sk.auth.vo.ResourcesVo;

import java.util.List;

public interface BaseResourceMapper extends BaseMapper<BaseResource> {
    List<ResourcesVo> listAllResourceApi();
}