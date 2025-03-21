package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 角色的资源
 */
@Data
@TableName(value = "base_role_resource_rel")
public class BaseRoleResourceRel {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 拥有资源;#def_resource
     */
    @TableField(value = "resource_id")
    private Long resourceId;

    /**
     * 所属应用;#def_application
     */
    @TableField(value = "application_id")
    private Long applicationId;

    /**
     * 所属角色;#base_role
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 最后更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id")
    private Long createdOrgId;
}