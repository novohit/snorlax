package com.wyu.snorlax.domain;

import lombok.Data;

import java.util.Map;

/**
 * 消息参数
 *
 * @author novo
 * @since 2023-04-12
 */
@Data
public class MessageParam {

    /**
     * 接收者 多个用逗号隔开
     */
    private String receiver;

    /**
     * 可变参数 占位符
     */
    private Map<String, String> variables;

    /**
     * 扩展参数
     * 可选
     */
    private Map<String, String> extra;
}
