package com.wyu.snorlax.deduplication.service;

import com.google.common.collect.Sets;
import com.wyu.snorlax.deduplication.DeduplicationContextHolder;
import com.wyu.snorlax.deduplication.DeduplicationRuleService;
import com.wyu.snorlax.deduplication.builder.DeduplicationParam;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author novo
 * @since 2023-04-22
 */
public abstract class AbstractDeduplicationService implements DeduplicationService {

    protected String deduplicationType;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier("deduplicate")
    private RedisScript<ArrayList> lockScript;

    @PostConstruct
    private void init() {
        DeduplicationContextHolder.setService(deduplicationType, this);
    }

    /**
     * 去重核心逻辑 如果子类需要自定义去重逻辑可以重写该方法
     * TODO Redis非原子性去重
     * 作为消息中心去重并不需要强一致性，从业务上来讲，偶尔并发时出现去重失败也没有太大的关系
     * 在性能和一致性上做一个权衡
     * 如果要保证原子性，要用lua脚本
     * <p>
     * 即使使用lua脚本实现了原子性，还会有一个问题
     * 去重功能是在发送消息之前就做了？会有消息发送失败的问题
     *
     * @param param
     */
    @Override
    @SuppressWarnings(value = {"unchecked"})
    public void deduplicate(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();
        List<String> keys = keys(taskInfo);
        String enableLua = System.getProperty(DeduplicationRuleService.ENABLE_LUA_KEY);
        // 使用Lua脚本实现原子性去重
        if (enableLua.equals("true")) {
            // 返回需要去重的key
            List<String> filterKeys = (List<String>) this.redisCache.redisTemplate.execute(lockScript, keys, param.getTime(), param.getCount());
            if (!CollectionUtils.isEmpty(filterKeys)) {
                Set<String> filterReceivers = taskInfo.getReceiver().stream()
                        .filter(receiver -> {
                            String key = deduplicateKey(taskInfo, receiver);
                            if (filterKeys.contains(key)) {
                                return true;
                            }
                            return false;
                        }).collect(Collectors.toSet());
                // 取出差集 不需要去重的用户
                Sets.SetView<String> difference = Sets.difference(taskInfo.getReceiver(), filterReceivers);
                // 这里不能提取到外层 因为是做差
                taskInfo.setReceiver(difference);
            }
        } else {
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
