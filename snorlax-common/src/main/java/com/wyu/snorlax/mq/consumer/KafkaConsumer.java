package com.wyu.snorlax.mq.consumer;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.wyu.snorlax.constant.Constants;
import com.wyu.snorlax.model.dto.CustomMessage;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author novo
 * @since 2023-04-15
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {Constants.MQ_TOPIC}, groupId = "user-group1", concurrency = "2")
    public void msgHandler(ConsumerRecord<?, String> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> value = Optional.ofNullable(record.value());
        if (value.isPresent()) {
            CustomMessage message = JSONObject.parseObject(value.get(), CustomMessage.class, JSONReader.Feature.SupportAutoType);
            log.info("接收成功,topic:[{}],partition:[{}],offset:[{}],message:[{}]", record.topic(), record.partition(), record.offset(), message);
            JSONArray jsonArray = (JSONArray) message.getContent();
            List<TaskInfo> taskInfoList = jsonArray.toJavaList(TaskInfo.class);
        }

        // 手动提交
    }
}
