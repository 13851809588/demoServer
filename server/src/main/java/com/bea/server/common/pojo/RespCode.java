package com.bea.server.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author yanjun
 */

@ToString
@AllArgsConstructor
@Getter
public enum  RespCode {

    SUCCESS(0,"成功"),
    FAIL(1000,"失败");

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String msg;
}
