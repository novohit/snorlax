package com.wyu.snorlax.mq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

/**
 * @author novo
 * @since 2023-04-14
 */
@Configuration
public class MQConfig {

    /**
     * 默认MQ实现
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "snorlax.mq", havingValue = "default", matchIfMissing = true)
    public MQProducer defaultMQProducer() {
        return new DefaultMQProducer();
    }

    @Bean
    @ConditionalOnProperty(name = "snorlax.mq", havingValue = "kafka")
    public MQProducer kafkaProducer() {
        return new KafkaMQProducer();
    }

    @Bean
    @ConditionalOnProperty(name = "snorlax.mq", havingValue = "rabbitmq")
    public MQProducer rabbitmqProducer() {
        return new RabbitMQProducer();
    }
}
