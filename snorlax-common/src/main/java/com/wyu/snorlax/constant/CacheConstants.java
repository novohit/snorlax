package com.wyu.snorlax.constant;

/**
 * @author novo
 * @since 2023-02-25 16:22
 */
public class CacheConstants {

    /**
     * 内容去重key redis key
     */
    public static final String CONTENT_DEDUPLICATE_KEY_PATTERN = "snorlax:deduplicate:content:%s:%s:%s";

    public static final String CHANNEL_DEDUPLICATE_KEY_PATTERN = "snorlax:deduplicate:channel:%s:%s:%s";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 图形验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "account-service:captcha:";

    /**
     * 图形验证码过期时间
     */
    public static final Integer CAPTCHA_EXPIRATION = 10;

    /**
     * 业务验证码 redis key
     */
    public static final String CHECK_CODE_KEY = "account-service:code:%s:%s";

    /**
     * 业务验证码过期时间min
     */
    public static final Integer CHECK_CODE_EXPIRATION = 5;

    /**
     * 业务验证码重复发送间隔60s
     */
    public static final Integer CHECK_CODE_REPEAT = 60 * 1000;

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";


}
