package com.wyu.snorlax.model.bo;

import lombok.*;

/**
 * @author novo
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsContent extends Content{

    /**
     * 短信发送内容
     */
    private String content;

    /**
     * 短信发送链接
     */
    private String url;
}
