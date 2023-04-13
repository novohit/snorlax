package com.wyu.snorlax.chain;

import com.wyu.snorlax.domain.MessageParam;
import com.wyu.snorlax.model.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendTaskModel extends ProcessModel{
    /**
     * 消息模板Id
     */
    private Long templateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;
}
