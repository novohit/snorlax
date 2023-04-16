package com.wyu.snorlax.chain;

import com.wyu.snorlax.processor.Processor;
import lombok.Data;

import java.util.List;

/**
 * 责任链
 *
 * @author novo
 * @since 2023-04-13
 */
@Data
public class ProcessChain {

    // 存储责任链结点
    private List<Processor> processorList;
}
