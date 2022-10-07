package com.mooez.event.commons.base;

/**
 * Description:
 * 常量接口
 * Author: Mooez
 * Date: 2022/9/18 22:22
 */
public interface Constants {

    /**
     * rabbitmq的普通事件交换机的名称
     */
    String RABBITMQ_NORMAL_EXCHANGE_NAME = "rabbitmq-normal-exchange";
    /**
     * rabbitmq的延迟事件交换机的名称
     */
    String RABBITMQ_DELAY_EXCHANGE_NAME = "rabbitmq-delay-exchange";

    /**
     *  队列的前缀名称
     */
    String RABBIT_QUEUE_PREFIX = "rabbitmq-event-queue-";
}
