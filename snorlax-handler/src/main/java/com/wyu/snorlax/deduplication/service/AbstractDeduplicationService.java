package com.wyu.snorlax.deduplication.service;

import com.google.common.collect.Sets;
import com.wyu.snorlax.deduplication.DeduplicationContextHolder;
import com.wyu.snorlax.deduplication.builder.DeduplicationParam;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author novo
 * @since 2023-04-22
 */
public abstract class AbstractDeduplicationService implements DeduplicationService {

    protected String deduplicationType;

    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    private void init() {
        DeduplicationContextHolder.setService(deduplicationType, this);
    }

    /**
     * TODO Redis非原子性去重
     * 作为消息中心去重并不需要强一致性，从业务上来讲，偶尔并发时出现去重失败也没有太大的关系
     * 在性能和一致性上做一个权衡
     * 如果要保证原子性，要用lua脚本
     *
     * @param param
     */
    @Override
    public void deduplicate(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();
        List<String> keys = keys(taskInfo);
        Map<String, Integer> cachaValues = this.redisCache.mGet(keys);
        // 需要去重的用户
        Set<String> filterReceivers = taskInfo.getReceiver().stream()
                .filter(receiver -> {
                    String key = deduplicateKey(taskInfo, receiver);
                    if (cachaValues.containsKey(key) && cachaValues.get(key) >= param.getCount()) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toSet());
        // 取出差集 不需要去重的用户
        Sets.SetView<String> difference = Sets.difference(taskInfo.getReceiver(), filterReceivers);
        // 不需要去重的用户redis自增
        updateCache(difference, cachaValues, param);
        taskInfo.setReceiver(difference);
    }

    /**
     * 构造去重Key
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    protected abstract String deduplicateKey(TaskInfo taskInfo, String receiver);

    /**
     * 获取所有去重key
     *
     * @param taskInfo
     * @return
     */
    private List<String> keys(TaskInfo taskInfo) {
        List<String> result = new ArrayList<>(taskInfo.getReceiver().size());
        for (String receiver : taskInfo.getReceiver()) {
            String key = deduplicateKey(taskInfo, receiver);
            result.add(key);
        }
        return result;
    }

    private void updateCache(Set<String> receivers, Map<String, Integer> cacheValues, DeduplicationParam param) {
        // Pipeline操作
        for (String receiver : receivers) {
            String key = deduplicateKey(param.getTaskInfo(), receiver);
            // 自增
            if (cacheValues.containsKey(key)) {
                cacheValues.put(key, cacheValues.get(key) + 1);
            } else {
                // 插入
                cacheValues.put(key, 1);
            }
        }
        this.redisCache.pipelineSetEx(cacheValues, param.getTime());
    }
}
