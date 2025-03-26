package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 应用
 */
@Data
@TableName(value = "base_application")
public class BaseApplication {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用标识
     */
    @TableField(value = "app_key")
    private String appKey;

    /**
     * 应用秘钥
     */
    @TableField(value = "app_secret")
    private String appSecret;

    /**
     * 应用名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 版本
     */
    @TableField(value = "version")
    private String version;

    /**
     * 应用类型;[10-自建应用 20-第三方应用]
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 重定向地址
     */
    @TableField(value = "redirect")
    private String redirect;

    /**
     * 简介
     */
    @TableField(value = "introduce")
    private String introduce;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 应用地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 是否公共应用;0-否 1-是
     */
    @TableField(value = "is_general")
    private Integer isGeneral;

    /**
     * 是否可见;0-否 1-是
     */
    @TableField(value = "is_visible")
    private Integer isVisible;

    /**
     * 排序
     */
    @TableField(value = "sort_value")
    private Integer sortValue;

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