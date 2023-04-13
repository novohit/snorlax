package com.wyu.snorlax.enums;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author novo
 * @since 2023-04-13
 */
public enum ChainType {

    /**
     * 发送流程链
     */
    SEND,

    /**
     * 撤回流程链
     */
    RECALL,


    ;

    public static ChainType toType(String code) {
        return Stream.of(values())
                .filter(chainCode-> Objects.equals(chainCode.name(), code))
                .findAny()
                .orElse(null);
    }

}
