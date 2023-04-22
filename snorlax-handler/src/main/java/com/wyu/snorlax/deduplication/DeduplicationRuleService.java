package com.wyu.snorlax.deduplication;

import com.wyu.snorlax.config.ConfigService;
import com.wyu.snorlax.constant.Constants;
import com.wyu.snorlax.deduplication.builder.DeduplicationParam;
import com.wyu.snorlax.enums.DeduplicationType;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2023-04-22
 */
@Service
@Slf4j
public class DeduplicationRuleService {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    public static final String ENABLE_LUA_KEY = "lua";

    @Autowired
    private ConfigService configService;

    /**
     * 暴露在最外层的去重接口
     *
     * @param taskInfo
     */
    public void duplicate(TaskInfo taskInfo) {
        String deduplicationConfig = this.configService.getProperty(DEDUPLICATION_RULE_KEY, Constants.EMPTY_JSON_OBJECT);
        String enableLua = this.configService.getProperty(ENABLE_LUA_KEY, "false");
        System.setProperty(ENABLE_LUA_KEY, enableLua);
        String type = DeduplicationType.CONTENT.name();
        DeduplicationParam param = DeduplicationContextHolder.builder(type).build(deduplicationConfig, taskInfo);
        if (param != null) {
            log.info("{}", param);
            DeduplicationContextHolder.selectService(type).deduplicate(param);
            // TODO
        }
    }
}
