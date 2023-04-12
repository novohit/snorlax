package com.wyu.snorlax.service;

import com.wyu.snorlax.domain.SendRequest;
import com.wyu.snorlax.domain.SendResponse;

/**
 * 消息发送接口
 *
 * @author novo
 * @since 2023-04-12
 */
public interface SendService {

    /**
     * 单文案发送接口
     *
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);

    /**
     * 多文案发送接口
     *
     * @param sendRequest
     * @return
     */
    SendResponse batchSend(SendRequest sendRequest);
}
