package com.wyu.snorlax.processor;

import com.wyu.snorlax.chain.ProcessContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-04-13
 */
@Component
@Slf4j
public class AssembleProcessor implements Processor {

    @Override
    public void doProcess(ProcessContext context) {
        log.info("装配参数");
    }
}
