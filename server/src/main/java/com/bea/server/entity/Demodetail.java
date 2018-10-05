package com.bea.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* Created by Mybatis Generator 2018/10/04
 * @author yanjun
 */
@Data
@TableName("t_demodetail")
public class Demodetail {
    /** 主键*/
    private Long id;

    /** 主表id*/
    private Long rid;

    /** 订单号*/
    private String orderno;

    /** 订单内容*/
    private String content;
}