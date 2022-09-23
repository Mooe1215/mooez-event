package com.mooez.event.action.apply.consumer;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Description:
 * 消费者 应用注解 - 标识当前需要监听的事件类型
 * Author: Mooez
 * Date: 2022/9/18 16:36
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MooezEvent {

    /**
     * 表示当前需要注册的事件名称
     * @return
     */
    String value();
}
