package com.wyu.snorlax.model.dto;

import com.wyu.snorlax.enums.SendIDType;
import com.wyu.snorlax.model.bo.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author novo
 * @since 2023-04-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {

    /**
     * 消息模板Id
     */
    private Long templateId;

    /**
     * 业务Id(数据追踪使用)
     */
    private Long bizId;

    /**
     * 接收者
     */
    private Set<String> receiver;

    /**
     * 发送渠道id类型
     */
    private SendIDType idType;

    /**
     * 发送渠道
     */
    private String sendChannel;

    /**
     * 10.运营类 20.技术类接口调用
     * 模板类型
     */
    private String templateType;

    /**
     * 消息类型
     * 10.通知类消息 20.营销类消息 30.验证码类消息
     */
    private String msgType;

    /**
     * 屏蔽类型
     */
    private String shieldType;

    /**
     * 发送文案模型
     * message_template表存储的content是JSON(所有内容都会塞进去)
     * 不同的渠道要发送的内容不一样(比如发push会有img，而短信没有)
     * 所以会有ContentModel
     */
    private Content content;

    /**
     * 发送账号（邮件下可有多个发送账号、短信可有多个发送账号..）
     */
    private Long channelAccountId;
}
