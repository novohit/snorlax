package com.wyu.snorlax.enums;

import com.wyu.snorlax.model.bo.SmsContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author novo
 * @since 2023-04-13
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ChannelType {

    SMS(30, "sms(短信)", SmsContent.class, "sms"),

    ;

    /**
     * 编码值
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;

    /**
     * 内容模型Class
     */
    private Class contentClass;

    /**
     * 英文标识
     */
    private String codeEn;

    /**
     * 通过code获取class
     * @param type
     * @return
     */
    public static Class getChannelClass(String type) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.name().equals(type)) {
                return value.getContentClass();
            }
        }
        return null;
    }


    public static ChannelType toType(String type) {
        return Stream.of(values())
                .filter(channelType-> Objects.equals(channelType.name(), type))
                .findAny()
                .orElse(null);
    }
}
