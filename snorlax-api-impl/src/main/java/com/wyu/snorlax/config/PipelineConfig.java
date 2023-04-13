package com.wyu.snorlax.config;

import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.processor.AssembleProcessor;
import com.wyu.snorlax.processor.PreParamCheckProcessor;
import com.wyu.snorlax.chain.ProcessChain;
import com.wyu.snorlax.chain.ProcessController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-04-13
 */
@Configuration
public class PipelineConfig {

    @Autowired
    private PreParamCheckProcessor preParamCheckProcessor;

    @Autowired
    private AssembleProcessor assembleProcessor;

    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数
     * 3. 后置参数校验
     * 4. 发送消息至MQ
     *
     * @return
     */
    @Bean("sendChain")
    public ProcessChain sendChain() {
        ProcessChain processChain = new ProcessChain();
        processChain.setProcessorList(Arrays.asList(preParamCheckProcessor, assembleProcessor));
        return processChain;
    }

    /**
     * pipeline流程控制器
     *
     * @return
     */
    @Bean
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessChain> processChainMap = new HashMap<>(4);
        processChainMap.put(ChainType.SEND.name(), sendChain());
        processController.setProcessChainMap(processChainMap);
        return processController;
    }
}
