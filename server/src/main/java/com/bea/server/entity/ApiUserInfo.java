package com.bea.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2018/10/05
 * @author yanjun
 */
@Data
@TableName("t_api_user_infos")
public class ApiUserInfo {
    /** 授权唯一标识*/
    @TableId
    private String appId;

    /** 用户状态,1：正常，0：无效*/
    private String status;

    /** 日请求量*/
    private Integer reqCountDay;

    /** 绑定IP地址多个使用“,”隔开*/
    private String bindIp;

    /** 备注*/
    private String mark;

    /** 授权密钥*/
    private byte[] appSecret;
}