package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 组织的角色
 */
@Data
@TableName(value = "base_org_role_rel")
public class BaseOrgRoleRel {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属部门;#base_org
     */
    @TableField(value = "org_id")
    private Long orgId;

    /**
     * 拥有角色;#base_role
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
}