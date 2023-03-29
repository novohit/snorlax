package com.wyu.snorlax.model;

import lombok.*;

/**
 * @author novo
 * @since 2023-03-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageRecord extends BaseModel{

    private String templateId;

    private String phone;

    private String supplierId;

    private String supplierName;

    private String msgContent;

    private String status;

    private String receiptContent;

    private String seriesId;

    private Integer chargingCount;

}
