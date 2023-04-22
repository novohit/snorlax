package com.wyu.snorlax.handler;

import com.wyu.snorlax.component.CodeSmsComponent;
import com.wyu.snorlax.component.SmsProperties;
import com.wyu.snorlax.component.ValueSmsComponent;
import com.wyu.snorlax.enums.ChannelType;
import com.wyu.snorlax.model.bo.SmsContent;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-04-21
 */
@Service
@Slf4j
public class SmsHandler extends BaseHandler {

    @Autowired
    private CodeSmsComponent codeSmsComponent;

    @Autowired
    private ValueSmsComponent valueSmsComponent;

    @Autowired
    private SmsProperties smsProperties;

    public SmsHandler() {
        this.channelType = ChannelType.SMS.name();
    }

    @Override
    public void handle(TaskInfo taskInfo) {
        log.info("{}", taskInfo);
        SmsContent smsContent = (SmsContent) taskInfo.getContent();
        String content = smsContent.getContent() + " " + smsContent.getUrl();
        //this.codeSmsComponent.send("13211291857", this.smsProperties.getTemplateId(), "444444");
        this.valueSmsComponent.send("13211291857", null, content);
    }
}
