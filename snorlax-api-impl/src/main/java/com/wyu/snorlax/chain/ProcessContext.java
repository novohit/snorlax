package com.wyu.snorlax.chain;

import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.model.vo.Resp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class ProcessContext<T extends ProcessModel> {

    /**
     * 标识责任链的code
     */
    private ChainType chainType;

    /**
     * 存储责任链上下文数据的模型
     */
    private T model;

    /**
     * 责任链中断的标识
     */
    private Boolean needBreak;

    /**
     * 流程处理的结果
     */
    Resp<Void> response;
}
