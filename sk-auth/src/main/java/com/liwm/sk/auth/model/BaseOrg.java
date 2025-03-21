package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 组织
 */
@Data
@TableName(value = "base_org")
public class BaseOrg {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 类型;[10-单位 20-部门]
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 简称
     */
    @TableField(value = "short_name")
    private String shortName;

    /**
     * 父组织
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 树层级
     */
    @TableField(value = "tree_grade")
    private Integer treeGrade;

    /**
     * 树路径;用id拼接树结构
     */
    @TableField(value = "tree_path")
    private String treePath;

    /**
     * 排序
     */
    @TableField(value = "sort_value")
    private Integer sortValue;

    /**
     * 状态;[0-禁用 1-启用]
     */
    @TableField(value = "`state`")
    private Boolean state;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

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
     * 修改时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 修改人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;
}