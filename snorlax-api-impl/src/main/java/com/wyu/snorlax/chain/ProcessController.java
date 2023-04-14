package com.wyu.snorlax.chain;

import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.processor.Processor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程控制器
 * 根据上下文执行相应的责任链
 *
 * @author novo
 * @since 2023-04-12
 */
@Data
@Slf4j
public class ProcessController {

    // 可以存储多条责任链
    private Map<String, ProcessChain> processChainMap = new HashMap<>();


    public void process(ProcessContext context) {
        //根据上下文的Code 执行不同的责任链
        ChainType chainType = context.getChainType();
        if (chainType == null) {
            log.error("责任链为空, chain_code:[{}]", chainType);
            throw new RuntimeException("责任链为空");
        }
        ProcessChain chain = processChainMap.get(chainType.name());

        List<Processor> processorList = chain.getProcessorList();
        //遍历某个责任链的流程节点
        for (Processor processor : processorList) {
            try {
                processor.doProcess(context);
                if (context.getNeedBreak()) {
                    break;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                //...
            }
        }
    }

}
