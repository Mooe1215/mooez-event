package com.zyd.event.standard.inter;

import com.zyd.event.standard.entity.MooezMessage;

/**
 * Description:
 * 核心服务层 - 消费端的处理接口
 * Author: Mooez
 * Date: 2022/9/19 0:01
 */
public interface CoreConsumerStandard {

    void msgHandler(MooezMessage message);
}
