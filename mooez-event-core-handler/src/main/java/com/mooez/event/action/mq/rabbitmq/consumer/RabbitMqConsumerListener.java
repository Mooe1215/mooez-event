package com.mooez.event.action.mq.rabbitmq.consumer;

import com.mooez.event.commons.base.Constants;
import com.mooez.event.standard.entity.MooezMessage;
import com.mooez.event.standard.inter.CoreConsumerStandard;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

/**
 * Description:
 * RabbitMQ消费者的监听器
 * Author: Mooez
 * Date: 2022/9/18 23:55
 */
public class RabbitMqConsumerListener {

    @Autowired
    private CoreConsumerStandard coreConsumerStandard;

    /**
     * 监听队列的回调方法
     */
    @RabbitListener(queues = Constants.RABBIT_QUEUE_PREFIX + "${spring.application.name}")
    public void handler(Message message){
        //获得RabbitMQ的Message对象中的内容
        byte[] body = message.getBody();
        //将字节数组反序列化成MooezMessage对象
        MooezMessage mooezMessage = (MooezMessage) SerializationUtils.deserialize(body);
        //调用核心服务层 实现消息的处理
        coreConsumerStandard.msgHandler(mooezMessage);
    }
}
