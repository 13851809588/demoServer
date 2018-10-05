package com.bea.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
* Created by Mybatis Generator 2018/10/01
 * @author yanjun
 */
@Data
@TableName("t_demo")
public class Demo {
    /** 主键ID*/
    private Long id;

    /** 姓名*/
    private String name;

    /** 年龄*/
    private Integer age;

    /** 邮箱*/
    private String email;

    /** 逻辑删除字段*/
    private Integer deleted;

    /**明细列表*/
    private List<Demodetail> demodetails;

}