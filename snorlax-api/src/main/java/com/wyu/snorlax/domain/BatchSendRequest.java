package com.wyu.snorlax.domain;

import lombok.Data;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-12
 */
@Data
public class BatchSendRequest {

    /**
     * 执行的业务类型 send: 发送 recall: 撤回
     */
    private String code;


    /**
     * 模板ID
     */
    private Long templateId;


    /**
     * 消息体参数
     */
    private List<MessageParam> messageParams;
}
