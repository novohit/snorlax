package com.wyu.snorlax.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author novo
 * @since 2023-04-12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendRequest {

    /**
     * 执行的业务类型 send: 发送 recall: 撤回
     */
    private String type;


    /**
     * 模板ID
     */
    private Long templateId;


    /**
     * 消息体参数
     */
    private MessageParam messageParam;
}
