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
public class MQTxConsumer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MQEnums.TRANS_GROUP.getValue());
        consumer.setNamesrvAddr(MQEnums.NAME_SRV_ADDR.getValue());
        consumer.subscribe(MQEnums.TX_TOPIC.getValue(), "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    switch (list.get(0).getTags()) {
                        case MQTags.ORDER_TAG:
                            System.out.println("收到事务消息执行article消费逻辑：" + list.get(0).getTags() + "-" + list.get(0).getKeys() + "-" + new String(list.get(0).getBody()));
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
