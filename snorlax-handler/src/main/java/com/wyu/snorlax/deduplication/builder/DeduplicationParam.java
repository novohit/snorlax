package com.wyu.snorlax.deduplication.builder;

import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author novo
 * @since 2023-04-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {

    private TaskInfo taskInfo;

    /**
     * 去重时间窗
     */
    private int time = 60;

    /**
     * 窗内去重次数阈值
     */
    private int count = 3;
}
