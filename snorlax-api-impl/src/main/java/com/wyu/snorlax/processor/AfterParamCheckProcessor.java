package com.wyu.snorlax.processor;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author novo
 * @since 2023-04-14
 */
@Component
@Slf4j
public class AfterParamCheckProcessor implements Processor<SendTaskModel> {

    @Override
    public void doProcess(ProcessContext<SendTaskModel> context) {
        log.info("后置参数校验");
        SendTaskModel sendTaskModel = context.getModel();
        List<TaskInfo> taskInfoList = sendTaskModel.getTaskInfo();

        for (TaskInfo taskInfo : taskInfoList) {
            // 过滤掉不合法的接收者
            Set<String> filterReceivers = taskInfo.getReceiver().stream()
                    .filter(receiver -> {
                        switch (taskInfo.getIdType()) {
                            case PHONE:
                                return CheckUtil.isPhone(receiver);
                            case EMAIL:
                                return CheckUtil.isEmail(receiver);
                            default:
                                return true;
                        }
                    }).collect(Collectors.toSet());
            taskInfo.setReceiver(filterReceivers);
        }
    }
}
