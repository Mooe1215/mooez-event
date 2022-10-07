package com.mooez.event.action.mq.rabbitmq.producer;

import com.mooez.event.commons.base.Constants;
import com.mooez.event.standard.entity.MooezMessage;
import com.mooez.event.standard.inter.MqProducerStandard;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

/**
 * Description:
 * RabbitMQ的消息发送实现类
 * Author: Mooez
 * Date: 2022/9/18 23:43
 */
public class RabbitMqProducerHandler implements MqProducerStandard {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息到RabbitMQ服务
     */
    @Override
    public void sendMessage2MQ(MooezMessage mooezMessage) {

        MessageProperties messageProperties = new MessageProperties();
        //设置消息的持久化
        //判断消息类型 如果是迅捷消息 消息即不需要持久化
        //如果是其他类型 消息需要持久化
        messageProperties.setDeliveryMode(mooezMessage.getMsgType() == 0 ? MessageDeliveryMode.NON_PERSISTENT : MessageDeliveryMode.PERSISTENT);

        //将发送的内容序列化
        byte[] body = SerializationUtils.serialize(mooezMessage);
        Message message = new Message(body, messageProperties);

        if (mooezMessage.getMsgType() == 2) {
            //延迟消息
        } else {
            //迅捷消息
            //发送到指定交换机
            rabbitTemplate.send(
                    Constants.RABBITMQ_NORMAL_EXCHANGE_NAME,
                    mooezMessage.getEventType(),
                    message);
        }

    }
}
