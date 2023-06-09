package com.wyu.snorlax.consumer;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.wyu.snorlax.config.DynamicThreadPoolContextHolder;
import com.wyu.snorlax.constant.Constants;
import com.wyu.snorlax.model.dto.CustomMessage;
import com.wyu.snorlax.domain.Task;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * @author novo
 * @since 2023-04-15
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 开启多例
@Slf4j
public class KafkaConsumer {

    @Autowired
    private DynamicThreadPoolContextHolder threadPoolContextHolder;

    /**
     * 发送消息是网络IO密集型，为了提高MQ消费速率，很自然的想到可以使用多线程，也就是使用线程池
     *
     * @param record
     * @param topic
     * @param groupId
     */
    @KafkaListener(topics = {Constants.MQ_TOPIC}, concurrency = "1")
    public void msgHandler(ConsumerRecord<?, String> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.GROUP_ID) String groupId) {
        Optional<String> value = Optional.ofNullable(record.value());
        if (value.isPresent()) {
            CustomMessage message = JSONObject.parseObject(value.get(), CustomMessage.class, JSONReader.Feature.SupportAutoType);
            //log.info("接收成功,topic:[{}],partition:[{}],offset:[{}],message:[{}]", record.topic(), record.partition(), record.offset(), message);
            JSONArray jsonArray = (JSONArray) message.getContent();
            List<TaskInfo> taskInfoList = jsonArray.toJavaList(TaskInfo.class);
            //log.info("groupId:[{}]", groupId);
            // 每个消费者组只关心自己组的消息
            if (!CollectionUtils.isEmpty(taskInfoList) && groupId.equals(CommonUtil.getGroupIdByTaskInfo(taskInfoList.get(0)))) {
                log.info("匹配 groupId:[{}]", groupId);
                log.info("接收成功,topic:[{}],partition:[{}],offset:[{}],message:[{}]", record.topic(), record.partition(), record.offset(), message);
                ExecutorService pool = threadPoolContextHolder.route(groupId);
                for (TaskInfo taskInfo : taskInfoList) {
                    Task task = new Task(taskInfo);
                    pool.execute(task);
                }
                // 手动提交
            }
        }
    }
}
