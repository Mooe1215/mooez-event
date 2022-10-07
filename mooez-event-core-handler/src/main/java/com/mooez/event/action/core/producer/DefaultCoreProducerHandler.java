package com.mooez.event.action.core.producer;

import com.mooez.event.standard.entity.MooezMessage;
import com.mooez.event.standard.inter.CoreProducerStandard;
import com.mooez.event.standard.inter.MqProducerStandard;
import com.mooez.event.standard.interceptor.MooezProducerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Description:
 * 生产者的默认核心服务层实现类
 * Author: Mooez
 * Date: 2022/9/18 15:46
 */
@Slf4j
public class DefaultCoreProducerHandler implements CoreProducerStandard {

    /**
     * 注入生产端拦截器
     */
    @Autowired(required = false)
    private List<MooezProducerInterceptor> producerInterceptors;

    /**
     *
     */
    @Autowired
    private MqProducerStandard mqProducerStandard;

    @Override
    public void sendMessage(MooezMessage mooezMessage) {
        log.debug("[event msg] 核心服务端接收到发送消息的请求！ - {}", mooezMessage);
        //调用生产端的拦截器链
        if (producerInterceptors != null) {
            //循环拦截器链进行相关处理
            for (MooezProducerInterceptor producerInterceptor : producerInterceptors) {
                try {
                    //判断当前拦截器是否需要处理当前消息
                    if (!producerInterceptor.isSupport(mooezMessage)) continue;

                    //当前拦截器需要处理当前的消息 - 接收新的消息(此处信息给出空返回值则可完成终止消息发送的操作)
                    mooezMessage = producerInterceptor.interceptor(mooezMessage);
                    //意味消息终止发送
                    if (mooezMessage == null) {
                        log.debug("[event msg] 当前消息终止发送！");
                        // 终止发送后 可以给开发者一个回调
                        return;
                    }
                } catch (Throwable t) {
                    log.error("[event msg interceptor] 拦截器发生异常!", t);
                }
            }
        }

        //调用MQ层的接口 将处理后的消息发送出去
        mqProducerStandard.sendMessage2MQ(mooezMessage);
    }
}
