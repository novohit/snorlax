package com.wyu.snorlax.deduplication;

import com.wyu.snorlax.deduplication.builder.DeduplicationBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-04-22
 */
public class DeduplicationContextHolder {
    private static final Map<String, DeduplicationBuilder> builderHolder = new HashMap<>(4);

    public static DeduplicationBuilder builder(String deduplicationType) {
        return builderHolder.get(deduplicationType);
    }

    public static void setBuilder(String deduplicationType, DeduplicationBuilder deduplicationBuilder) {
        builderHolder.put(deduplicationType, deduplicationBuilder);
    }
}
