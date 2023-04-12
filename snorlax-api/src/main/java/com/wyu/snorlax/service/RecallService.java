package com.wyu.snorlax.service;

import com.wyu.snorlax.domain.SendRequest;
import com.wyu.snorlax.domain.SendResponse;

/**
 * 消息撤回接口
 *
 * @author novo
 * @since 2023-04-12
 */
public interface RecallService {

    /**
     * 消息撤回
     *
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
