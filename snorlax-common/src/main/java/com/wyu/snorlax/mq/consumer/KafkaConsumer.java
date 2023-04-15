package com.wyu.snorlax.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-04-15
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {"helloworld"}, groupId = "user-group1", concurrency = "2")
    public void msgHandler(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("接收成功,topic:[{}],partition:[{}],offset:[{}],res:[{}]", record.topic(), record.partition(), record.offset(), record.value());
        // 手动提交
    }
}
