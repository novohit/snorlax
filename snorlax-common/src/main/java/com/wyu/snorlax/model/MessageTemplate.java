package com.wyu.snorlax.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * @author novo
 * @since 2023-03-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class MessageTemplate extends BaseModel {

    private String title;

    /**
     * 模板状态
     */
    private Integer status;

    /**
     * 模板工单
     */
    private String flowId;

    /**
     * 消息
     */
    private Integer msgStatus;

    private Long cronTaskId;

    private String cronCrowdPath;

    private String expectPushTime;

    /**
     * 发送渠道id类型
     */
    private String idType;

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
     * 模板内容
     */
    private String templateContent;


    /**
     * 发送账号（邮件下可有多个发送账号、短信可有多个发送账号..）
     */
    private Long channelAccountId;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新者
     */
    private String modifier;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 业务方
     */
    private String team;
}
