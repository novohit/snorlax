package com.wyu.snorlax.deduplication.builder;

import com.wyu.snorlax.enums.DeduplicationType;
import com.wyu.snorlax.model.dto.TaskInfo;
import org.springframework.stereotype.Component;

/**
 * 要加入容器 不然不会自动构建出来 builderHolder为空
 *
 * @author novo
 * @since 2023-04-22
 */
@Component
public class ChannelDeduplicationBuilder extends AbstractDeduplicationBuilder {

    public ChannelDeduplicationBuilder() {
        this.deduplicationType = DeduplicationType.CHANNEL.name();
    }

    @Override
    public DeduplicationParam build(String deduplicationConfig, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = assembleParam(deduplicationConfig, taskInfo);
        // TODO 设置去重类型
        return deduplicationParam;
    }
}
