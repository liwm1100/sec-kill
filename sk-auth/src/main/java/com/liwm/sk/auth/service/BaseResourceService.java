package com.liwm.sk.auth.service;

import com.liwm.sk.auth.model.BaseResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwm.sk.auth.vo.ResourcesVo;

import java.util.List;

public interface BaseResourceService extends IService<BaseResource>{

    List<ResourcesVo> listAllResourceApi();
}
