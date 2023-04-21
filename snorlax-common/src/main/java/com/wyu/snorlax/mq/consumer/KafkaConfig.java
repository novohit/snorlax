package com.wyu.snorlax.mq.consumer;

import com.wyu.snorlax.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;

import javax.annotation.PostConstruct;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author novo
 * @since 2023-04-21
 */
@Configuration
@ConditionalOnProperty(name = "snorlax.mq", havingValue = "kafka")
public class KafkaConfig {

    @Autowired
    private ApplicationContext context;

    /**
     * 获取得到所有的groupId
     */
    private static final List<String> groupIds = CommonUtil.getAllGroupIds();

    private static int index = 0;

    /**
     * 获取消费者的方法名称  KafkaConsumer.msgHandler
     */
    private static final String CONSUMER_METHOD_NAME = KafkaConsumer.class.getSimpleName() + "." + KafkaConsumer.class.getMethods()[0].getName();

    /**
     * 创建渠道*消息类型个实例
     */
    @PostConstruct
    public void init() {
        for (int i = 0; i < groupIds.size(); i++) {
            context.getBean(KafkaConsumer.class);
        }
    }

    /**
     * 相当于的aop切面 获取@KafkaListener注解 更改其中的参数
     * 官方文档:
     * <a href="https://docs.spring.io/spring-kafka/reference/html/#kafkalistener-attrs">https://docs.spring.io/spring-kafka/reference/html/#kafkalistener-attrs</a>
     *
     * @return
     */
    @Bean
    public static KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer groupIdEnhancer() {
        return new KafkaListenerAnnotationBeanPostProcessor.AnnotationEnhancer() {

            /**
             *
             * @param attrs AnnotationAttributes类型 存储了注解中的k-v参数
             * @param element 方法
             * @return
             */
            @Override
            public Map<String, Object> apply(Map<String, Object> attrs, AnnotatedElement element) {
                if (element instanceof Method) {
                    String name = ((Method) element).getDeclaringClass().getSimpleName() + "." + ((Method) element).getName();
                    if (CONSUMER_METHOD_NAME.equals(name)) {
                        // 为每个实例设置不同的groupId
                        attrs.put("groupId", groupIds.get(index++));
                    }
                }
                return attrs;
            }
        };
    }


}
