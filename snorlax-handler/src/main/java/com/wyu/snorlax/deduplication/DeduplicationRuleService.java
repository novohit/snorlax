package com.wyu.snorlax.deduplication;

import com.wyu.snorlax.deduplication.builder.DeduplicationParam;
import com.wyu.snorlax.model.dto.TaskInfo;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-04-22
 */
@Service
public class DeduplicationRuleService {
    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    /**
     * 暴露在最外层的去重接口
     *
     * @param taskInfo
     */
    public void duplicate(TaskInfo taskInfo) {
        String type = "dd";
        DeduplicationParam param = DeduplicationContextHolder.builder(type).build("", taskInfo);
        if (param != null) {
            // TODO
        }
    }
}
