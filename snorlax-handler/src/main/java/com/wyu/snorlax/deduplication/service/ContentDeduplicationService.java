package com.wyu.snorlax.deduplication.service;

import com.alibaba.fastjson2.JSONObject;
import com.wyu.snorlax.constant.CacheConstants;
import com.wyu.snorlax.enums.DeduplicationType;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.util.CommonUtil;
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

    /**
     * 根据发送的内容作为key
     * time内相同用户收到count条相同的内容
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    @Override
    protected String deduplicateKey(TaskInfo taskInfo, String receiver) {
        return String.format(CacheConstants.CONTENT_DEDUPLICATE_KEY_PATTERN, taskInfo.getTemplateId(), receiver, taskInfo.getContent().toString());
    }
}
