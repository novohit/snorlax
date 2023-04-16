package com.wyu.snorlax.processor;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.ProcessModel;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.domain.MessageParam;
import com.wyu.snorlax.enums.BizCodeEnum;
import com.wyu.snorlax.model.vo.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author novo
 * @since 2023-04-13
 */
@Component
@Slf4j
public class PreParamCheckProcessor implements Processor<SendTaskModel> {

    private final static Integer MAX_RECEIVER = 100;

    @Override
    public void doProcess(ProcessContext<SendTaskModel> context) {
        log.info("前置参数校验");
        SendTaskModel taskModel = context.getModel();
        Long templateId = taskModel.getTemplateId();
        List<MessageParam> messageParamList = taskModel.getMessageParamList();
        // 1. 判断templateId和参数
        if (templateId == null || CollectionUtils.isEmpty(messageParamList)) {
            context.setNeedBreak(true)
                    .setResponse(Resp.buildResult(BizCodeEnum.PARAM_ERROR));
            return;
        }

        // 2. 过滤receiver为null的参数
        List<MessageParam> result = messageParamList.stream()
                .filter(messageParam -> StringUtils.hasText(messageParam.getReceiver()))
                .collect(Collectors.toList());
        taskModel.setMessageParamList(result);

        // 3. 过滤receiver大于100的请求
        boolean exceed = result.stream().anyMatch(messageParam -> messageParam.getReceiver().split(",").length > MAX_RECEIVER);
        if (exceed) {
            context.setNeedBreak(true)
                    .setResponse(Resp.buildResult(BizCodeEnum.RECEIVER_LIMIT));
            return;
        }
    }
}
