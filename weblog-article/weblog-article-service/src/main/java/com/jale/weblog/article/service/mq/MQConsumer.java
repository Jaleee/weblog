package com.jale.weblog.article.service.mq;

import com.jale.weblog.common.rocketmq.MQEnums;
import com.jale.weblog.common.rocketmq.MQTags;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MQConsumer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MQEnums.GROUP.getValue());
        consumer.setNamesrvAddr(MQEnums.NAME_SRV_ADDR.getValue());
        consumer.subscribe(MQEnums.TOPIC.getValue(), "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    switch (list.get(0).getTags()) {
                        case MQTags.ORDER_TAG:
                            System.out.println("收到并处理了消息：" + list.get(0).getTags() + "-" + list.get(0).getKeys() + "-" + new String(list.get(0).getBody()));
                            break;

                        default:
                            break;
                    }
                } catch (Exception e) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }





}
