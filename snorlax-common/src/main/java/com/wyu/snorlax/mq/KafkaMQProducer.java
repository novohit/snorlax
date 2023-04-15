package com.wyu.snorlax.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.wyu.snorlax.model.dto.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author novo
 * @since 2023-04-15
 */
@Slf4j
public class KafkaMQProducer implements MQProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(String topic, CustomMessage customMessage, String tagId) {

    }

    @Override
    public void send(String topic, CustomMessage customMessage) {
        String jsonStr = JSON.toJSONString(customMessage, JSONWriter.Feature.WriteClassName);
        this.kafkaTemplate.send(topic, jsonStr).addCallback(success -> {
            RecordMetadata recordMetadata = success.getRecordMetadata();
            int partition = recordMetadata.partition();
            Object value = success.getProducerRecord().value();
            long offset = recordMetadata.offset();
            log.info("发送成功,topic:[{}],partition:[{}],offset:[{}],message:[{}]", topic, partition, offset, value);
        }, failure -> {
            String message = failure.getMessage();
            log.error("发送失败, message:[{}]", message);
        });
    }
}
