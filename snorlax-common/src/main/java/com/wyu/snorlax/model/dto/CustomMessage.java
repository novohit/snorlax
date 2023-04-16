package com.wyu.snorlax.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author novo
 * @since 2023-04-14
 */
@Builder
@Data
public class CustomMessage implements Serializable {

    /**
     * 消息队列的消息id
     */
    private String messageId;


    /**
     * 操作账号
     */
    private Long accountNo;


    /**
     * 业务id 备用
     */
    private String bizId;

    /**
     * 消息体
     */
    private Object content;

    /**
     * 备注
     */
    private String remark;
}
