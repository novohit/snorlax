package com.wyu.snorlax.enums;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author novo
 * @since 2023-04-14
 */
public enum SendIDType {

    PHONE,

    EMAIL,

    ;

    public static SendIDType toType(String type) {
        return Stream.of(values())
                .filter(sendIDType-> Objects.equals(sendIDType.name(), type))
                .findAny()
                .orElse(null);
    }
}
