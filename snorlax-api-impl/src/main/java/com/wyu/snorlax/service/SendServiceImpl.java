package com.wyu.snorlax.service;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.ProcessController;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.domain.SendRequest;
import com.wyu.snorlax.domain.SendResponse;
import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.model.vo.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author novo
 * @since 2023-04-12
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    @Override
    public SendResponse send(SendRequest sendRequest) {

        // 1. 构建责任链上下文所需参数
        SendTaskModel taskModel = SendTaskModel.builder()
                .templateId(sendRequest.getTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        // 2. 构建责任链上下文
        ProcessContext context = ProcessContext.builder()
                .chainType(ChainType.toType(sendRequest.getType()))
                .model(taskModel)
                .needBreak(false)
                .response(Resp.success())
                .build();

        // 3. 通过流程控制器执行责任链
        this.processController.process(context);
        return new SendResponse(context.getResponse().getCode(), context.getResponse().getMsg());
    }

    @Override
    public SendResponse batchSend(SendRequest sendRequest) {
        return null;
    }
}
