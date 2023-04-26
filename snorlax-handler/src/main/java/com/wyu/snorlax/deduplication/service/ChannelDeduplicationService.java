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
public class ChannelDeduplicationService extends AbstractDeduplicationService {

    public ChannelDeduplicationService() {
        this.deduplicationType = DeduplicationType.CHANNEL.name();
    }

    /**
     * 业务去重
     * 根据发送渠道+消息类型+receiver作为key 例如 SMS:notice
     * 一天内只允许同一用户接收到同一渠道+消息类型的消息三次
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    @Override
    protected String deduplicateKey(TaskInfo taskInfo, String receiver) {
        return String.format(CacheConstants.CHANNEL_DEDUPLICATE_KEY_PATTERN, taskInfo.getChannelType(), taskInfo.getMsgType(), receiver);
    }
}
