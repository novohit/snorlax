package com.wyu.snorlax.deduplication;

import com.wyu.snorlax.deduplication.builder.DeduplicationBuilder;
import com.wyu.snorlax.deduplication.service.DeduplicationService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-04-22
 */
public class DeduplicationContextHolder {
    private static final Map<String, DeduplicationBuilder> builderHolder = new HashMap<>(4);

    private static final Map<String, DeduplicationService> deduplicationServiceHolder = new HashMap<>(4);

    public static DeduplicationBuilder builder(String deduplicationType) {
        return builderHolder.get(deduplicationType);
    }

    public static void setBuilder(String deduplicationType, DeduplicationBuilder deduplicationBuilder) {
        builderHolder.put(deduplicationType, deduplicationBuilder);
    }

    public static DeduplicationService selectService(String deduplicationType) {
        return deduplicationServiceHolder.get(deduplicationType);
    }

    public static void setService(String deduplicationType, DeduplicationService deduplicationService) {
        deduplicationServiceHolder.put(deduplicationType, deduplicationService);
    }
}
