package com.wyu.snorlax.deduplication.builder;

import com.wyu.snorlax.enums.DeduplicationType;
import com.wyu.snorlax.model.dto.TaskInfo;

/**
 * @author novo
 * @since 2023-04-22
 */
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder {

    public ContentDeduplicationBuilder() {
        this.deduplicationType = DeduplicationType.CONTENT.name();
    }

    @Override
    public DeduplicationParam build(String deduplicationConfig, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = assembleParam(deduplicationConfig, taskInfo);
        // TODO 设置去重类型
        return deduplicationParam;
    }
}
