package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 员工所在部门
 */
@Data
@TableName(value = "base_employee_org_rel")
public class BaseEmployeeOrgRel {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联机构
     */
    @TableField(value = "org_id")
    private Long orgId;

    /**
     * 关联员工
     */
    @TableField(value = "employee_id")
    private Long employeeId;

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