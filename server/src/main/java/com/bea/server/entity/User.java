package com.bea.server.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2018/10/01
 * @author yanjun
 */
@Data
@TableName("T_SYS_USER")
public class User {
    /** 标识*/
    private Integer userid;

    /** 用户组id*/
    private Integer usergroupid;

    private Integer roleid;

    /** 登录名*/
    private String loginname;

    /** 用户名*/
    private String username;

    /** 证书序列号*/
    private String certsn;

    /** 邮箱*/
    private String email;

    /** 电话*/
    private String telephone;

    /** 手机*/
    private String mobilephone;

    /** ip范围*/
    private String ipaddress;

    /** 口令策略标识*/
    private Integer policyid;

    /** 密码*/
    private String password;

    /** 描述*/
    private String description;

    /** 状态
            1 : 正常
            2 : 删除
            3 : 锁定*/
    private Integer status;

    /** 是否系统内置
            0 : 不内置
            1 : 内置*/
    private Boolean issysdefault;

    /** 创建时间*/
    private Date createtime;

    /** 最近登录时间*/
    private Date lastlogintime;

    /** 填写的采用主题的css样式名称*/
    private String themetype;

    /** IP前缀*/
    private Integer prefix;

    private String startstandardip;

    private String endstandardip;

    private Boolean verifyipaddr;

    private Integer updatepwdcount;
}