package com.liwm.sk.auth.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liwm.sk.auth.dto.ApiDTO;
import com.liwm.sk.auth.dto.ResourcesDTO;
import com.liwm.sk.auth.dto.UserDTO;
import com.liwm.sk.auth.model.*;
import com.liwm.sk.auth.service.*;
import com.liwm.sk.auth.vo.ResourcesVo;
import com.liwm.sk.common.dto.Result;
import com.liwm.sk.common.utils.JsonBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private BaseEmployeeService baseEmployeeService;
    @Autowired
    private BaseEmployeeRoleRelService baseEmployeeRoleRelService;
    @Autowired
    private BaseRoleService baseRoleService;
    @Autowired
    private BaseRoleResourceRelService baseRoleResourceRelService;
    @Autowired
    private BaseResourceService baseResourceService;
    @Autowired
    private BaseApiService baseApiService;

    @GetMapping("/permission-list")
    public Result<List<String>> getPermissionList(Long userId) {
        BaseEmployee employee = baseEmployeeService.getOne(Wrappers.lambdaQuery(BaseEmployee.class).eq(BaseEmployee::getUserId, userId));
        List<BaseEmployeeRoleRel> employeeRoleRels = baseEmployeeRoleRelService.list(Wrappers.lambdaQuery(BaseEmployeeRoleRel.class).eq(BaseEmployeeRoleRel::getEmployeeId, employee.getId()));
        if(CollUtil.isEmpty(employeeRoleRels)) {
            return Result.success(Collections.emptyList());
        }
        List<Long> roles = employeeRoleRels.stream().map(BaseEmployeeRoleRel::getRoleId).collect(Collectors.toList());
        List<BaseRoleResourceRel> roleList = baseRoleResourceRelService.list(Wrappers.lambdaQuery(BaseRoleResourceRel.class).in(BaseRoleResourceRel::getRoleId, roles));
        if(CollUtil.isEmpty(roleList)) {
            return Result.success(Collections.emptyList());
        }
        List<Long> resoureIds = roleList.stream().map(BaseRoleResourceRel::getResourceId).collect(Collectors.toList());
        List<BaseResource> resources = baseResourceService.list(Wrappers.lambdaQuery(BaseResource.class).in(BaseResource::getId, resoureIds));
        List<String> permissionCodes = CollUtil.isEmpty(resources) ? new ArrayList<>() : resources.parallelStream().map(BaseResource::getCode).filter(ObjectUtil::isNotEmpty).distinct().collect(Collectors.toList());
        return Result.success(permissionCodes);
    }

    @GetMapping("/role-list")
    public Result<List<String>> getRoleList(Long userId) {
        BaseEmployee employee = baseEmployeeService.getOne(Wrappers.lambdaQuery(BaseEmployee.class).eq(BaseEmployee::getUserId, userId));
        List<BaseEmployeeRoleRel> employeeRoleRels = baseEmployeeRoleRelService.list(Wrappers.lambdaQuery(BaseEmployeeRoleRel.class).eq(BaseEmployeeRoleRel::getEmployeeId, employee.getId()));
        if(CollUtil.isEmpty(employeeRoleRels)) {
            return Result.success(Collections.emptyList());
        }
        List<Long> roles = employeeRoleRels.stream().map(BaseEmployeeRoleRel::getRoleId).collect(Collectors.toList());
        List<BaseRole> roleList = baseRoleService.list(Wrappers.lambdaQuery(BaseRole.class).in(BaseRole::getId, roles));
        if(CollUtil.isEmpty(roleList)) {
            return Result.success(Collections.emptyList());
        }
        return Result.success(roleList.stream().map(BaseRole::getName).collect(Collectors.toList()));
    }

    @GetMapping("/user-info")
    public Result<UserDTO> userInfo(Long userId) {
        BaseUser user = baseUserService.getById(userId);
        return Result.success(JsonBeanUtil.copyObject(user, UserDTO.class));
    }

    @GetMapping("/listApi")
    public Result<List<ApiDTO>> listApi(int authorizeType) {
        List<BaseApi> apis = baseApiService.list(Wrappers.lambdaQuery(BaseApi.class).eq(BaseApi::getAuthorizeType,authorizeType));
        return Result.success(JsonBeanUtil.copyList(apis, ApiDTO.class));
    }
    @GetMapping("/listAllResourceApi")
    public Result<List<ResourcesDTO>> listAllResourceApi() {
        List<ResourcesVo> resourcesVos = baseResourceService.listAllResourceApi();
        return Result.success(JsonBeanUtil.copyList(resourcesVos, ResourcesDTO.class));
    }
}
