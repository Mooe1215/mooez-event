package com.mooez.event.config;

import com.mooez.event.action.apply.consumer.IMooezEventHandler;
import com.mooez.event.action.core.consumer.DefaultCoreConsumerHandler;
import com.mooez.event.action.core.producer.DefaultCoreProducerHandler;
import com.mooez.event.commons.utils.ApplicationUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * 事件总线的自动装配
 * Author: Mooez
 * Date: 2022/9/19 0:29
 */
@Configuration
@ComponentScan("com.mooez")
public class MooezEventConfiguration {

    /**
     * 核心服务层 - 生产者的默认处理器
     * @return
     */
    @Bean
    public DefaultCoreProducerHandler getDefaultCoreProducerHandler(){
        return new DefaultCoreProducerHandler();
    }

    /**
     * 核心服务层 - 消费者的默认处理器
     * 使用事件处理器进行是否进行消费者判断
     * @return
     */
    @Bean
    @ConditionalOnBean(IMooezEventHandler.class)
    public DefaultCoreConsumerHandler getDefaultCoreConsumerHandler(){
        return new DefaultCoreConsumerHandler();
    }

    /**
     * 手动处理SpringIOC的工具类
     * @return
     */
    @Bean
    public ApplicationUtils getApplicationUtils(){
        return new ApplicationUtils();
    }
}
