package com.mooez.event.action.apply.producer;


import com.mooez.event.commons.utils.ApplicationUtils;
import com.mooez.event.standard.entity.MooezMessage;
import com.mooez.event.standard.inter.CoreProducerStandard;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * 事件发布工具类 - 生产者调用
 * Author: Mooez
 * Date: 2022/9/17 10:15
 */
public class MooezEventTemplate {

    /**
     * 获得 核心服务层生产端的操作对象
     */
    private static CoreProducerStandard coreProducerStandard;

    static {
        coreProducerStandard = ApplicationUtils.getBean(CoreProducerStandard.class);
    }

    /**
     * 发送迅捷消息 - 不保证可靠性 性能最高 实时性最好
     *
     * @param eventType
     * @param data
     */
    public static <T> void sendQuickly(String eventType, T data) {
        send(eventType, data, 0, null);
    }

    /**
     * 发送可靠消息 - 保证消息可达 略微损耗性能和实时性
     *
     * @param eventType
     * @param data
     * @param <T>
     */
    public static <T> void sendReliable(String eventType, T data) {
        send(eventType, data, 1, null);
    }

    /**
     * 发送延迟消息
     *
     * @param eventType
     * @param data
     * @param time
     * @param <T>
     */
    public static <T> void sendDelay(String eventType, T data, long time, TimeUnit unit) {
        send(eventType, data, 2, unit.toMillis(time));
    }


    /**
     * 统一的发送方法
     *
     * @param eventType
     * @param data
     * @param msgType
     * @param delayTimeMs
     * @param <T>
     */
    private static <T> void send(String eventType, T data, Integer msgType, Long delayTimeMs) {
        //构建MooezMassage对象
        MooezMessage mooezMessage = new MooezMessage.Builder<>(eventType, data)
                .setId(UUID.randomUUID().toString()) //消息的ID号 -暂定UUID
                .setSendTime(new Date()) //当前发送时间
                .setMsgType(msgType) //发送类型
                .setDelayTime(delayTimeMs) //延迟时间
                .build();
        //调用下层将消息发出
        coreProducerStandard.sendMessage(mooezMessage);
    }
}
