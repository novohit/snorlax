package com.wyu.snorlax.domain;

import cn.hutool.core.collection.CollUtil;
import com.wyu.snorlax.deduplication.DeduplicationRuleService;
import com.wyu.snorlax.handler.HandlerContextHolder;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.util.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * @author novo
 * @since 2023-04-21
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Runnable {

    private TaskInfo taskInfo;

    private DeduplicationRuleService deduplicationRuleService = SpringContextUtil.getBean(DeduplicationRuleService.class);

    @Override
    public void run() {
        log.info("thread:[{}],taskInfo:[{}]", Thread.currentThread().getName(), taskInfo);

        // 2.平台通用去重
        if (!CollectionUtils.isEmpty(taskInfo.getReceiver())) {
            this.deduplicationRuleService.duplicate(taskInfo);
            log.info("去重后:{}", taskInfo.getReceiver());
        }
        // 路由到具体的Handler执行
        if (!CollectionUtils.isEmpty(taskInfo.getReceiver())) {
            //HandlerContextHolder.route(taskInfo.getChannelType()).handle(taskInfo);
        }
    }

    public Task(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
