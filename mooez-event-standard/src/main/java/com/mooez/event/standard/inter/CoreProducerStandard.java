package com.mooez.event.standard.inter;

import com.mooez.event.standard.entity.MooezMessage;

/**
 * Description:
 * 生产端的核心服务层规范接口
 * Author: Mooez
 * Date: 2022/9/18 11:27
 */
public interface CoreProducerStandard {

    void sendMessage(MooezMessage mooezMessage);
}
