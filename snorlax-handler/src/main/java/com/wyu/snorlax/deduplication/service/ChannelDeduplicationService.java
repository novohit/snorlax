package com.wyu.snorlax.deduplication.service;

import com.wyu.snorlax.constant.CacheConstants;
import com.wyu.snorlax.enums.DeduplicationType;
import com.wyu.snorlax.model.dto.TaskInfo;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-04-22
 */
@Service
public class ChannelDeduplicationService extends AbstractDeduplicationService{

    public ChannelDeduplicationService() {
        this.deduplicationType = DeduplicationType.CHANNEL.name();
    }

    @Override
    protected String deduplicateKey(TaskInfo taskInfo, String receiver) {
        return String.format(CacheConstants.CHANNEL_DEDUPLICATE_KEY_PATTERN, taskInfo.getChannelType(), receiver);
    }
}
