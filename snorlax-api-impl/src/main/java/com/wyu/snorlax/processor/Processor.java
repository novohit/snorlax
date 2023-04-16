package com.wyu.snorlax.processor;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.ProcessModel;

/**
 * 责任链结点接口
 *
 * @author novo
 * @since 2023-04-13
 */
public interface Processor<T extends ProcessModel> {

    /**
     * 处理逻辑
     */
    void doProcess(ProcessContext<T> context);
}
