package com.wyu.snorlax.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * @author novo
 * @since 2023-04-26
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChannelAccount extends BaseModel {

    /**
     * 账号名
     */
    private String name;

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 账号配置
     */
    private String config;

    /**
     * 创建人
     */
    private String creator;
}
