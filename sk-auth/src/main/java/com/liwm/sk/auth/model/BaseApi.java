package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * api接口
 */
@Data
@TableName(value = "base_api")
public class BaseApi {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    @TableField(value = "application_id")
    private Long applicationId;

    /**
     * 所属服务;取配置文件中 spring.application.name
     */
    @TableField(value = "spring_application_name")
    private String springApplicationName;

    /**
     * 控制器类名
     */
    @TableField(value = "controller")
    private String controller;

    /**
     * 请求类型
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 接口名;接口上的注释
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 接口地址;
     */
    @TableField(value = "uri")
    private String uri;

    /**
     * 授权方式;0授权，1忽略授权，2公开
     */
    @TableField(value = "authorize_type")
    private Integer authorizeType;

    /**
     * 是否启用0否，1是
     */
    @TableField(value = "is_enabled")
    private Integer isEnabled;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 最后更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;
}