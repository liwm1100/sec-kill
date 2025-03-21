package com.liwm.sk.auth.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwm.sk.auth.model.BaseEmployee;
import com.liwm.sk.auth.mapper.BaseEmployeeMapper;
import com.liwm.sk.auth.service.BaseEmployeeService;
@Service
public class BaseEmployeeServiceImpl extends ServiceImpl<BaseEmployeeMapper, BaseEmployee> implements BaseEmployeeService{

}
