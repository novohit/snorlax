package com.wyu.snorlax.chain;

import com.wyu.snorlax.domain.MessageParam;
import com.wyu.snorlax.model.MessageTemplate;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.*;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendTaskModel extends ProcessModel {
    /**
     * 消息模板Id
     */
    private Long templateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 组装后的发送消息
     */
    private List<TaskInfo> taskInfo;
}
