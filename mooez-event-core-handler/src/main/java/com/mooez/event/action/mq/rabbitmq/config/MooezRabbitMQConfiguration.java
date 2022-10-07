package com.mooez.event.action.mq.rabbitmq.config;

import com.mooez.event.action.apply.consumer.MooezEvent;
import com.mooez.event.action.apply.consumer.MooezEventHandler;
import com.mooez.event.action.mq.rabbitmq.consumer.RabbitMqConsumerListener;
import com.mooez.event.action.mq.rabbitmq.producer.RabbitMqProducerHandler;
import com.mooez.event.commons.base.Constants;
import com.mooez.event.commons.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 事件总线中 在rabbitmq的配置类
 * Author: Mooez
 * Date: 2022/9/18 16:58
 */
@Configuration
@Slf4j
public class MooezRabbitMQConfiguration {

    /**
     * 生产者的配置
     */
    @Configuration
    public static class RabbitMQProducerConfiguration {

        {
            log.debug("[RabbitMQ module Loader] RabbitMQ的生产端的配置加载！");
        }

        /**
         * 创建一个普通事件交换机
         */
        @Bean
        public DirectExchange getEventExchange() {
            return new DirectExchange(Constants.RABBITMQ_NORMAL_EXCHANGE_NAME, true, false);
        }

        /**
         * 创建一个延迟事件交换机
         */
        @Bean
        public CustomExchange getDelayExchange(){
            Map<String ,Object> args = new HashMap<>();
            args.put("x-delayed-type","topic");
            return new CustomExchange(Constants.RABBITMQ_DELAY_EXCHANGE_NAME,"x-delayed-message",true,false,args);
        }


        /**
         * 生产端 - RabbitMQ的处理实现类
         */
        @Bean
        public RabbitMqProducerHandler getRabbitMqProducerHandler(){
            return new RabbitMqProducerHandler();
        }
    }

    /**
     * 消费者的配置
     *
     * ConditionalOnBean(MooezEventHandler.class) - 如果当前IOC容器中 有一个类型的Bean是MooezEventHandler类型，当前的这个配置类就会被Spring加载
     */
    @Configuration
    @ConditionalOnBean(MooezEventHandler.class)
    public static class RabbitMQConsumerConfiguration {

        {
            log.debug("[RabbitMQ module Loader] RabbitMQ的消费端的配置加载！");
        }

        @Value("${spring.application.name}")
        public String appName;

        /**
         * 获取消费端的 自定义的监听器集合
         */
        @Autowired
        public List<MooezEventHandler> eventHandlers;

        /**
         * RabbitMQ的默认消费者对象
         */
        @Bean
        public RabbitMqConsumerListener getRabbitMqConsumerListener(){
            return new RabbitMqConsumerListener();
        }

        /**
         * 创建一个消费端的队列
         */
        @Bean
        public Queue getEventQueue() {
            return new Queue(Constants.RABBIT_QUEUE_PREFIX + appName, true, false, false);
        }

        /**
         * 将队列和普通交换机绑定
         */
        @Bean
        public Binding getEventBinding(DirectExchange eventExchange, Queue eventQueue) {

            //循环所有的消费者监听器 - 获取事件类型
            for (MooezEventHandler eventHandler : eventHandlers) {
                //通过反射获取监听器对象上注解的事件类型
                MooezEvent mooezEvent = eventHandler.getClass().getAnnotation(MooezEvent.class);
                //注解不存在则跳过
                if (mooezEvent == null)
                    throw new RuntimeException("The MooezEvent annotation event type must be added to the bean of type MooezEventHandler!");

                //获取交换机绑定队列的对象
                Binding binding = BindingBuilder
                        .bind(eventQueue)
                        .to(eventExchange)
                        .with(mooezEvent.value());

                //手动将Bean注册到IOC容器中
                ApplicationUtils.registerBean(binding.getClass().getName() + binding.hashCode(), binding);
            }
            //此处无需返回 - 已经在循环中将Binding对象注册进ioc容器当中
            return null;
        }

        /**
         * 将队列和延迟交换机绑定
         */
        @Bean
        public Binding getDelayEventBinding(CustomExchange delayExchange, Queue eventQueue) {

            //循环所有的消费者监听器 - 获取事件类型
            for (MooezEventHandler eventHandler : eventHandlers) {
                //通过反射获取监听器对象上注解的事件类型
                MooezEvent mooezEvent = eventHandler.getClass().getAnnotation(MooezEvent.class);
                //注解不存在则跳过
                if (mooezEvent == null)
                    throw new RuntimeException("The MooezEvent annotation event type must be added to the bean of type MooezEventHandler!");

                //获取交换机绑定队列的对象
                Binding binding = BindingBuilder
                        .bind(eventQueue)
                        .to(delayExchange)
                        .with(mooezEvent.value())
                        .and(new HashMap<>());

                //手动将Bean注册到IOC容器中
                ApplicationUtils.registerBean(binding.getClass().getName() + binding.hashCode(), binding);
            }
            //此处无需返回 - 已经在循环中将Binding对象注册进ioc容器当中
            return null;
        }
    }
}
