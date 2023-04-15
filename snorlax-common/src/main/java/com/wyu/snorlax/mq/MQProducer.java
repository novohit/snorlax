package com.wyu.snorlax.mq;

import com.wyu.snorlax.model.dto.CustomMessage;

/**
 * @author novo
 * @since 2023-04-14
 */
public interface MQProducer {

    /**
     * 发送消息
     *
     * @param topic
     * @param customMessage
     * @param tagId
     */
    void send(String topic, CustomMessage customMessage, String tagId);


    /**
     * 发送消息
     *
     * @param topic
     * @param customMessage
     */
    void send(String topic, CustomMessage customMessage);
}
