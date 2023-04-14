package com.wyu.snorlax.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息参数
 *
 * @author novo
 * @since 2023-04-12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageParam {

    /**
     * 接收者 多个用逗号隔开
     */
    private String receiver;

    /**
     * 可变参数 占位符
     */
    @Builder.Default
    // Builder模式会导致缺省值失效 保留缺省值用 @Builder.Default 作用于FIELD
    private Map<String, String> variables = new HashMap<>();

    /**
     * 扩展参数
     * 可选
     */
    private Map<String, String> extra;
}
