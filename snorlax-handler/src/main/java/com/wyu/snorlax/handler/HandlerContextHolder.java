package com.wyu.snorlax.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储不同渠道的映射关系
 * <p>
 * 为什么这里要加入容器 因为BaseHandler在handler模块中 TaskInfo要用HandlerContextHolder只能通过容器获取
 *
 * @author novo
 * @since 2023-04-21
 */
public class HandlerContextHolder {

    private static final Map<String, BaseHandler> handlers = new HashMap<>(32);

    public static void set(String channelType, BaseHandler handler) {
        handlers.put(channelType, handler);
    }

    public static BaseHandler route(String channelType) {
        return handlers.get(channelType);
    }
}
