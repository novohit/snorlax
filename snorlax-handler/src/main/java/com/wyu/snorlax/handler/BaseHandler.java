package com.wyu.snorlax.handler;

import com.wyu.snorlax.model.dto.TaskInfo;

import javax.annotation.PostConstruct;

/**
 * @author novo
 * @since 2023-04-21
 */
public abstract class BaseHandler {

    /**
     * 标识渠道的Code
     * 子类初始化的时候指定
     */
    protected String channelType;

    /**
     * 子类初始化后自动调用该方法去初始化渠道与Handler的映射关系
     */
    @PostConstruct
    private void init() {
        HandlerContextHolder.set(channelType, this);
    }

    /**
     * 统一处理推送
     *
     * @param taskInfo
     * @return
     */
    public abstract void handle(TaskInfo taskInfo);
}
