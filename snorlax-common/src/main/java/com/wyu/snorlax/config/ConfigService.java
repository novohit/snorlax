package com.wyu.snorlax.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author novo
 * @since 2023-04-22
 */
@Service
@Slf4j
public class ConfigService {

    /**
     * 本地配置
     */
    private static final String PROPERTIES_PATH = "local.properties";

    private static final Properties properties = new Properties();

    @PostConstruct
    private void init() {
        try (InputStream inputStream = ConfigService.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("加载本地配置失败", e);
        }
        log.info("加载本地配置成功");
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
