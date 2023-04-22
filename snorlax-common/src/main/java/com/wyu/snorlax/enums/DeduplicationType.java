package com.wyu.snorlax.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author novo
 * @since 2023-04-22
 */
@AllArgsConstructor
@Getter
public enum DeduplicationType {

    /**
     * 相同内容去重
     */
    CONTENT(10, "N分钟相同内容去重"),

    /**
     * 渠道接受消息 频次 去重
     */
    CHANNEL(20, "一天内N次相同渠道去重"),

    ;

    private final Integer code;

    private final String description;
}
