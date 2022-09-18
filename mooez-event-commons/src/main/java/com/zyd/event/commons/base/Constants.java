package com.zyd.event.commons.base;

/**
 * Description:
 * 常量接口
 * Author: Mooez
 * Date: 2022/9/18 22:22
 */
public interface Constants {

    /* rabbitmq的事件交换机的名称 */
    String RABBITMQ_NORMAL_EXCHANGE_NAME = "event-exchange";

    /* 队列的前缀名称 */
    String RABBIT_QUEUE_PREFIX = "event-queue-";
}
