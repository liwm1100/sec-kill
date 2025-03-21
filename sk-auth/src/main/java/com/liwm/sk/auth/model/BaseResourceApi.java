package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 资源接口
 */
@Data
@TableName(value = "base_resource_api")
public class BaseResourceApi {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源ID
     */
    @TableField(value = "resource_id")
    private Long resourceId;

    /**
     * api接口id
     */
    @TableField(value = "api_id")
    private Long apiId;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Byte isDeleted;

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