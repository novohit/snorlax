package com.wyu.snorlax.processor;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.constant.Constants;
import com.wyu.snorlax.enums.BizCodeEnum;
import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.model.dto.CustomMessage;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.model.vo.Resp;
import com.wyu.snorlax.mq.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-14
 */
@Component
@Slf4j
public class SendMQProcessor implements Processor<SendTaskModel> {

    @Autowired
    private MQProducer mqProducer;

    @Override
    public void doProcess(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getModel();
        try {
            if (ChainType.SEND.equals(context.getChainType())) {
                List<TaskInfo> taskInfoList = sendTaskModel.getTaskInfo();
                CustomMessage message = CustomMessage.builder()
                        .content(taskInfoList)
                        .build();
                this.mqProducer.send(Constants.MQ_TOPIC, message);
            } else if (ChainType.RECALL.equals(context.getChainType())) {
                // TODO
            }
        } catch (Exception e) {
            context.setNeedBreak(true)
                    .setResponse(Resp.buildResult(BizCodeEnum.MQ_PRODUCER_ERROR));
            log.error("mq send error", e);
        }
    }
}
