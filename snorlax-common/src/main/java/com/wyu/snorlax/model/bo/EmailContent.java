package com.wyu.snorlax.model.bo;

import lombok.*;

/**
 * @author novo
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailContent extends Content{

    /**
     * 标题
     */
    private String title;

    /**
     * 内容(可写入HTML)
     */
    private String content;

    /**
     * 邮件附件链接
     */
    private String url;

}
