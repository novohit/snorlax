package com.wyu.snorlax.config;

import com.dtp.core.thread.DtpExecutor;
import com.wyu.snorlax.util.CommonUtil;
import com.wyu.snorlax.util.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author novo
 * @since 2023-04-21
 */
@Component
public class DynamicThreadPoolContextHolder {

    @Autowired
    private ThreadPoolUtil threadPoolUtil;

    private final Map<String, ExecutorService> threadPoolContextHolder = new HashMap<>(32);

    /**
     * 获取得到所有的groupId
     */
    private static final List<String> groupIds = CommonUtil.getAllGroupIds();


    /**
     * 给每个group分配一个线程池
     */
    @PostConstruct
    public void init() {
        /**
         * example ThreadPoolName:austin.im.notice
         *
         * 可以通过apollo配置：dynamic-tp-apollo-dtp.yml  动态修改线程池的信息
         */
        for (String groupId : groupIds) {
            DtpExecutor executor = ThreadPoolUtil.dtpExecutor(groupId);
            threadPoolUtil.register(executor);
            threadPoolContextHolder.put(groupId, executor);
        }
    }

    /**
     * 路由到对应的线程池
     *
     * @param groupId
     * @return
     */
    public ExecutorService route(String groupId) {
        return this.threadPoolContextHolder.get(groupId);
    }
}
