package com.mooez.event.standard.interceptor;

import com.mooez.event.standard.entity.MooezMessage;

/**
 * Description:
 * 消费者拦截器
 * Author: Mooez
 * Date: 2022/9/19 0:06
 */
public interface MooezConsumerInterceptor {

    boolean isSupport(MooezMessage message);

    MooezMessage consumerInterceptor(MooezMessage mooezMessage);
}
