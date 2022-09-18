package com.zyd.event.standard.inter;

import com.zyd.event.standard.entity.MooezMessage;

/**
 * Description:
 * MQ层的消息发送规范接口
 * Author: Mooez
 * Date: 2022/9/18 16:18
 */
public interface MqProducerStandard {

    void sendMessage2MQ(MooezMessage mooezMessage);
}
