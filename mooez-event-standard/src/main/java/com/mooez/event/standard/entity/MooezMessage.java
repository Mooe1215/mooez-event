package com.mooez.event.standard.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 * 封装消息对象 - 构造器模式
 * Author: Mooez
 * Date: 2022/9/17 11:05
 */
@Data
public class MooezMessage<T> implements Serializable {
    //私有化构造方法
    private MooezMessage(){}

    //消息的唯一标识
    private String id;

    //消息类型 0-迅捷消息 1-可靠消息 2-延迟消息
    private Integer msgType;

    //延迟时间 - 单位ms
    private Long delayTime;

    //发送时间
    private Date sendTime;

    //接收时间
    private Date recvTime;

    //事件类型 - 开发者定义
    private String eventType;

    //事件内容
    private T msg;

    //额外的属性 - 提供给开发者进行扩展
    private Map<String , Objects> attr;

    /**
     * 创建一个构建器类
     * @param <T>
     */
    public static class Builder<T>{
        //消息的唯一标识
        private String id;

        //消息类型 0-迅捷消息 1-可靠消息 2-延迟消息
        private Integer msgType;

        //延迟时间 - 单位ms
        private Long delayTime;

        //发送时间
        private Date sendTime;

        //接收时间
        private Date recvTime;

        //事件类型 - 开发者定义
        private String eventType;

        //事件内容
        private T msg;

        //额外的属性 - 提供给开发者进行扩展
        private Map<String , Object> attr;

        /**
         * 构建器设置一个构造方法
         * @param eventType
         * @param msg
         */
        public Builder(String eventType,T msg){
            this.eventType = eventType;
            this.msg = msg;
            this.attr = new HashMap<>();
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setMsgType(Integer msgType) {
            this.msgType = msgType;
            return this;
        }

        public Builder setDelayTime(Long delayTime) {
            this.delayTime = delayTime;
            return this;
        }

        public Builder setSendTime(Date sendTime) {
            this.sendTime = sendTime;
            return this;
        }

        public Builder setRecvTime(Date recvTime) {
            this.recvTime = recvTime;
            return this;
        }

        public Builder addAttr(String key,Object value){
            this.attr.put(key, value);
            return this;
        }

        /**
         * 构建对象
         * @return
         */
        public MooezMessage build(){
            MooezMessage mooezMessage = new MooezMessage();
            mooezMessage.setId(id);
            mooezMessage.setEventType(eventType);
            mooezMessage.setDelayTime(delayTime);
            mooezMessage.setSendTime(sendTime);
            mooezMessage.setRecvTime(recvTime);
            mooezMessage.setMsg(msg);
            mooezMessage.setMsgType(msgType);
            mooezMessage.setAttr(attr);
            return mooezMessage;
        }
    }
}
