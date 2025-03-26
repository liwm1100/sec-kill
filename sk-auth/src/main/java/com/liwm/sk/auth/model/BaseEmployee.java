package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 员工
 */
@Data
@TableName(value = "base_employee")
public class BaseEmployee {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否默认员工;[0-否 1-是]
     */
    @TableField(value = "is_default")
    private Integer isDefault;

    /**
     * 所属岗位
     */
    @TableField(value = "position_id")
    private Long positionId;

    /**
     * 用户
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 状态;[0-禁用 1-启用]
     */
    @TableField(value = "`state`")
    private Integer state;

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