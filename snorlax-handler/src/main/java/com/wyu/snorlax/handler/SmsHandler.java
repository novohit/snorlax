package com.wyu.snorlax.handler;

import com.wyu.snorlax.enums.ChannelType;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-04-21
 */
@Service
@Slf4j
public class SmsHandler extends BaseHandler {

    public SmsHandler() {
        this.channelType = ChannelType.SMS.name();
    }

    @Override
    public void handle(TaskInfo taskInfo) {
        log.info("{}", taskInfo);
    }
}
