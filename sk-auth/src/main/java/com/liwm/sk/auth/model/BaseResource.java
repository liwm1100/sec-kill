package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 资源
 */
@Data
@TableName(value = "base_resource")
public class BaseResource {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用ID;#def_application
     */
    @TableField(value = "application_id")
    private Long applicationId;

    /**
     * 编码;唯一编码，用于区分资源
     */
    @TableField(value = "code")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 类型;[10-目录,20-菜单 40-按钮 ]
     */
    @TableField(value = "resource_type")
    private String resourceType;

    /**
     * 父级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 描述;resource_type=接口时表示接口说明
     */
    @TableField(value = "`describe`")
    private String describe;

    /**
     * 状态;[0-禁用 1-启用]
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 排序;默认升序
     */
    @TableField(value = "sort_value")
    private Integer sortValue;

    /**
     * 创建人id
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新人id
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;
}