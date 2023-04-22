package com.wyu.snorlax.deduplication;

import com.alibaba.fastjson2.JSONArray;
import com.wyu.snorlax.config.ConfigService;
import com.wyu.snorlax.constant.Constants;
import com.wyu.snorlax.deduplication.builder.DeduplicationParam;
import com.wyu.snorlax.enums.DeduplicationType;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-22
 */
@Service
@Slf4j
public class DeduplicationRuleService {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    public static final String DEDUPLICATION_POLICY_KEY = "deduplicationPolicy";

    public static final String ENABLE_LUA_KEY = "lua";

    @Autowired
    private ConfigService configService;

    /**
     * 暴露在最外层的去重接口
     *
     * @param taskInfo
     */
    public void duplicate(TaskInfo taskInfo) {
        // 获取设置的去重策略 deduplicationPolicy = ["content","channel"]
        String deduplicationPolicy = this.configService.getProperty(DEDUPLICATION_POLICY_KEY, Constants.EMPTY_VALUE_JSON_ARRAY);
        // 获取每种策略的具体去重配置
        String deduplicationConfig = this.configService.getProperty(DEDUPLICATION_RULE_KEY, Constants.EMPTY_JSON_OBJECT);
        JSONArray jsonArray = JSONArray.parseArray(deduplicationPolicy);
        List<String> deduplicationList = jsonArray.toJavaList(String.class);

        String enableLua = this.configService.getProperty(ENABLE_LUA_KEY, "false");
        System.setProperty(ENABLE_LUA_KEY, enableLua);

        // 去重
        deduplicationList.forEach(deduplicationType -> {
            try {
                DeduplicationParam param = DeduplicationContextHolder.builder(deduplicationType).build(deduplicationConfig, taskInfo);
                if (param != null) {
                    log.info("{}", param);
                    DeduplicationContextHolder.selectService(deduplicationType).deduplicate(param);
                    // TODO
                }
            } catch (Exception e) {
                log.error("deduplicationType:[{}] 去重失败", deduplicationType, e);
            }
        });
    }
}
