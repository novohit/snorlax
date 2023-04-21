package com.wyu.snorlax.domain;

import com.wyu.snorlax.handler.HandlerContextHolder;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void run() {
        log.info("thread:[{}],taskInfo:[{}]", Thread.currentThread().getName(), taskInfo);

        // 路由到具体的Handler执行
        if (!CollectionUtils.isEmpty(taskInfo.getReceiver())) {
            HandlerContextHolder.route(taskInfo.getChannelType()).handle(taskInfo);
        }
    }
}
