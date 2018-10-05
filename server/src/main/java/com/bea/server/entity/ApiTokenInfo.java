package com.bea.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2018/10/05
 * @author yanjun
 */
@Data
@TableName("t_api_token_infos")
public class ApiTokenInfo {
    /**
     * 流水号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 应用id浩
     */
    private String appId;

    /** 生成token时间（秒单位）*/
    private String createtime;

    /**
     * token值
     */
    private byte[] token;
}