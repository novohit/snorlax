package com.wyu.snorlax.deduplication.builder;

import com.wyu.snorlax.model.dto.TaskInfo;

/**
 * @author novo
 * @since 2023-04-22
 */
public interface DeduplicationBuilder {

    String DEDUPLICATION_CONFIG_PRE = "deduplication_";

    /**
     * 根据去重配置构建去重参数
     *
     * @param deduplicationConfig
     * @param taskInfo
     * @return
     */
    DeduplicationParam build(String deduplicationConfig, TaskInfo taskInfo);
}
