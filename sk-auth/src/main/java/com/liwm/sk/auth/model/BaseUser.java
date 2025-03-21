package com.liwm.sk.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 */
@Data
@TableName(value = "base_user")
public class BaseUser {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名;大小写数字下划线
     */
    @TableField(value = "username")
    private String username;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机;1开头11位纯数字
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 用户类型，1内部用户，2外部用户
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 状态;[0-禁用 1-启用]
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 输错密码时间
     */
    @TableField(value = "password_error_last_time")
    private Date passwordErrorLastTime;

    /**
     * 密码错误次数
     */
    @TableField(value = "password_error_num")
    private Integer passwordErrorNum;

    /**
     * 密码过期时间
     */
    @TableField(value = "password_expire_time")
    private Date passwordExpireTime;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 密码盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

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