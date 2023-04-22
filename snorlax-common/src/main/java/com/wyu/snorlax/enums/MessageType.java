package com.wyu.snorlax.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author novo
 * @since 2023-04-21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MessageType {
    /**
     * 通知类消息
     */
    NOTICE(10, "通知类消息", "notice"),
    /**
     * 营销类消息
     */
    MARKETING(20, "营销类消息", "marketing"),
    /**
     * 验证码消息
     */
    CODE(30, "验证码消息", "code");

    /**
     * 编码值
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;


    /**
     * 英文标识
     */
    private String codeEn;

    public static MessageType toType(String type) {
        return Stream.of(values())
                .filter(messageType-> Objects.equals(messageType.name(), type))
                .findAny()
                .orElse(null);
    }
}
