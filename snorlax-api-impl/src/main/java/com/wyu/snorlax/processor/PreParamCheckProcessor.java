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
public class PreParamCheckProcessor implements Processor {

    @Override
    public void doProcess(ProcessContext context) {
        System.out.println("前置参数校验");
    }
}
