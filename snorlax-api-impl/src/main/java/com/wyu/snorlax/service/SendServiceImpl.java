package com.wyu.snorlax.service;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.ProcessController;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.domain.SendRequest;
import com.wyu.snorlax.domain.SendResponse;
import com.wyu.snorlax.enums.ChainType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author novo
 * @since 2023-04-12
 */
@Service
public class SendServiceImpl implements SendService{

    @Autowired
    private ProcessController processController;

    @Override
    public SendResponse send(SendRequest sendRequest) {

        SendTaskModel taskModel = SendTaskModel.builder()
                .templateId(sendRequest.getTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        ProcessContext context = ProcessContext.builder()
                .chainType(ChainType.toType(sendRequest.getType()))
                .model(taskModel)
                .needBreak(false)
                .response("success")
                .build();

        this.processController.process(context);
        return new SendResponse();
    }

    @Override
    public SendResponse batchSend(SendRequest sendRequest) {
        return null;
    }
}
