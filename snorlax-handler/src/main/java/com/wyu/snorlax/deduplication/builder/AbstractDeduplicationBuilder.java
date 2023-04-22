package com.wyu.snorlax.deduplication.builder;

import com.alibaba.fastjson2.JSONObject;
import com.wyu.snorlax.deduplication.DeduplicationContextHolder;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * @author novo
 * @since 2023-04-22
 */
@Slf4j
public abstract class AbstractDeduplicationBuilder implements DeduplicationBuilder {

    protected String deduplicationType;

    @PostConstruct
    public void init() {
        DeduplicationContextHolder.setBuilder(deduplicationType, this);
    }

    protected DeduplicationParam assembleParam(String duplicationConfig, TaskInfo taskInfo) {
        // 获取去重的配置 封装为去重配置对象
        JSONObject jsonObject = JSONObject.parseObject(duplicationConfig);
        if (jsonObject != null) {
            // 忽略大小写
            String key = DEDUPLICATION_CONFIG_PRE + deduplicationType;
            String lowerCase = key.toLowerCase();
            String upperCase = key.toUpperCase();
            String param = jsonObject.getString(lowerCase);
            if (param == null) {
                param = jsonObject.getString(upperCase);
            }
            try {
                DeduplicationParam deduplicationParam = JSONObject.parseObject(param, DeduplicationParam.class);
                deduplicationParam.setTaskInfo(taskInfo);
                return deduplicationParam;
            } catch (Exception e) {
                log.error("配置解析异常", e);
            }
        }
        return null;
    }

}
