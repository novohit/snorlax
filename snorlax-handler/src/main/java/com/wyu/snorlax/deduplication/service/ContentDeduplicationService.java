package com.wyu.snorlax.deduplication.service;

import com.wyu.snorlax.enums.DeduplicationType;
import com.wyu.snorlax.model.dto.TaskInfo;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-04-22
 */
@Service
public class ContentDeduplicationService extends AbstractDeduplicationService {

    public ContentDeduplicationService() {
        this.deduplicationType = DeduplicationType.CONTENT.name();
    }

    @Override
    protected String deduplicateKey(TaskInfo taskInfo, String receiver) {
        return receiver;
    }
}
