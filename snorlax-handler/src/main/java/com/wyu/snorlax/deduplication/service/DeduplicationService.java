package com.wyu.snorlax.deduplication.service;

import com.wyu.snorlax.deduplication.builder.DeduplicationParam;

/**
 * @author novo
 * @since 2023-04-22
 */
public interface DeduplicationService {

    /**
     * 去重接口
     *
     * @param param
     */
    void deduplicate(DeduplicationParam param);
}
