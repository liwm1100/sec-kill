package com.liwm.sk.gateway.feign.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    private Long id;
    /**
     * 用户名;大小写数字下划线
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机;1开头11位纯数字
     */
    private String mobile;

    /**
     * 用户类型，1内部用户，2外部用户
     */
    private Integer type;

    /**
     * 状态;[0-禁用 1-启用]
     */
    private Integer state;

    /**
     * 输错密码时间
     */
    private Date passwordErrorLastTime;

    /**
     * 密码错误次数
     */
    private Integer passwordErrorNum;

    /**
     * 密码过期时间
     */
    private Date passwordExpireTime;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
}
