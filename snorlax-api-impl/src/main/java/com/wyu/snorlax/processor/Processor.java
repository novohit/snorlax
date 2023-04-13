package com.wyu.snorlax.processor;

import com.wyu.snorlax.chain.ProcessContext;

/**
 * 责任链结点接口
 *
 * @author novo
 * @since 2023-04-13
 */
public interface Processor {

    /**
     * 处理逻辑
     */
    void doProcess(ProcessContext context);
}
