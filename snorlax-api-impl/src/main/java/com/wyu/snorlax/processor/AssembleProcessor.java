package com.wyu.snorlax.processor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.reflect.Reflection;
import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.domain.MessageParam;
import com.wyu.snorlax.enums.BizCodeEnum;
import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.enums.ChannelType;
import com.wyu.snorlax.model.MessageTemplate;
import com.wyu.snorlax.model.bo.Content;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.model.vo.Resp;
import com.wyu.snorlax.repository.MessageTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.annotations.common.reflection.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author novo
 * @since 2023-04-13
 */
@Component
@Slf4j
public class AssembleProcessor implements Processor<SendTaskModel> {

    @Autowired
    private MessageTemplateRepository templateRepository;

    // 定义${开头 ，}结尾的占位符
    private static final PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}");

    @Override
    public void doProcess(ProcessContext<SendTaskModel> context) {
        log.info("装配参数");
        SendTaskModel sendTaskModel = context.getModel();
        Long templateId = sendTaskModel.getTemplateId();

        this.templateRepository.findById(templateId)
                .ifPresentOrElse(messageTemplate -> {
                    if (context.getChainType() == ChainType.SEND) {
                        List<TaskInfo> taskInfos = assembleTaskInfo(sendTaskModel.getMessageParamList(), messageTemplate);
                        sendTaskModel.setTaskInfo(taskInfos);
                    } else if (context.getChainType() == ChainType.RECALL) {
                        // TODO
                    } else {
                        // TODO
                    }
                }, () -> context
                        .setNeedBreak(true)
                        .setResponse(Resp.buildResult(BizCodeEnum.TEMPLATE_NOT_FOUND)));

    }

    /**
     * 组装 TaskInfo 任务消息
     *
     * @param messageParamList
     * @param template
     */
    private List<TaskInfo> assembleTaskInfo(List<MessageParam> messageParamList, MessageTemplate template) {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        for (MessageParam messageParam : messageParamList) {

            TaskInfo taskInfo = TaskInfo.builder()
                    .templateId(template.getId())
                    .bizId(1L)
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(","))))
                    .idType(template.getIdType())
                    .sendChannel(template.getSendChannel())
                    .templateType(template.getTemplateType())
                    .msgType(template.getMsgType())
                    .shieldType(template.getShieldType())
                    .channelAccountId(template.getChannelAccountId())
                    .content(getContent(template, messageParam)).build();

            taskInfoList.add(taskInfo);
        }

        return taskInfoList;

    }

    private Content getContent(MessageTemplate template, MessageParam messageParam) {
        String sendChannel = template.getSendChannel();
        Class channelClass = ChannelType.getChannelClass(sendChannel);
        // 得到模板的 msgContent 和 入参
        Map<String, String> variables = messageParam.getVariables();
        JSONObject jsonObject = JSON.parseObject(template.getTemplateContent());

        // 通过反射 组装出 content
        // 为什么要通过反射组装，因为每个类的变量是不一样的，我们只能通过class来获取有多少Field[]字段
        assert channelClass != null;
        Content content = null;
        try {
            Field[] fields = FieldUtils.getAllFields(channelClass);
            content = (Content) channelClass.newInstance();
            System.out.println(content);
            for (Field field : fields) {
                // 这个value可能有占位符
                String value = jsonObject.getString(field.getName());
                // 解析占位符
                String finalValue = placeholderHelper.replacePlaceholders(value, variables::get);
                FieldUtils.writeField(field, content, finalValue, true);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(content);
        return content;
    }
}
