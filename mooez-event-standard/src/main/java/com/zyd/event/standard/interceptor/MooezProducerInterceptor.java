package com.zyd.event.standard.interceptor;

import com.zyd.event.standard.entity.MooezMessage;

/**
 * Description:
 * 拦截器的接口
 * Author: Mooez
 * Date: 2022/9/18 15:50
 */
public interface MooezProducerInterceptor {

    /**
     * 判断当前拦截器 是否需要对当前消息进行拦截
     * @param mooezMessage
     * @return true - 拦截器的interceptor触发 / false - 跳过当前拦截器
     */
    boolean isSupport(MooezMessage mooezMessage);

    /**
     * 发送端拦截方法
     * @param mooezMessage 需要处理的消息对象
     * @return 处理过的消息对象 如果返回null，则表示中断当前消息的发送
     */
    MooezMessage interceptor(MooezMessage mooezMessage);
}
