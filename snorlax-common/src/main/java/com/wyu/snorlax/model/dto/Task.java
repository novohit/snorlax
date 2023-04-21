package com.wyu.snorlax.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author novo
 * @since 2023-04-21
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Runnable {

    private TaskInfo taskInfo;


    @Override
    public void run() {
        log.info("thread:[{}],taskInfo:[{}]", Thread.currentThread().getName(), taskInfo);
    }
}
