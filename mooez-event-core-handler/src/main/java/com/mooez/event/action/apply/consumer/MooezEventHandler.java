package com.mooez.event.action.apply.consumer;

import com.mooez.event.standard.entity.MooezMessage;

/**
 * Description:
 * 事件处理器
 * Author: Mooez
 * Date: 2022/9/18 16:38
 */
public interface MooezEventHandler<T> {

    /**
     * 事件处理
     * @param data - 基本数据
     * @param mooezMessage - 额外信息数据
     */
    void eventHandler(T data, MooezMessage mooezMessage);
}
