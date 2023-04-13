package com.wyu.snorlax.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author novo
 * @since 2023-04-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendResponse {

    private Integer code;


    private String msg;
}
