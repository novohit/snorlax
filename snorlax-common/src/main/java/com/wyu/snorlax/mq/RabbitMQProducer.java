package com.wyu.snorlax.mq;

import com.wyu.snorlax.model.dto.CustomMessage;

/**
 * @author novo
 * @since 2023-04-14
 */
public class RabbitMQProducer implements MQProducer{

    @Override
    public void send(String topic, CustomMessage customMessage, String tagId) {

    }

    @Override
    public void send(String topic, CustomMessage customMessage) {

    }
}
