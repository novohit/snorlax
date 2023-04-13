package com.wyu.snorlax.chain;

import com.wyu.snorlax.enums.ChainType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 责任链上下文
 *
 * @author novo
 * @since 2023-04-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessContext {

    /**
     * 标识责任链的code
     */
    private ChainType chainType;

    /**
     * 存储责任链上下文数据的模型
     */
    private ProcessModel model;

    /**
     * 责任链中断的标识
     */
    private Boolean needBreak;

    /**
     * 流程处理的结果
     */
    String response;
}
