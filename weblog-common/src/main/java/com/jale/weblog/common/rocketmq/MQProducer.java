package com.jale.weblog.common.rocketmq;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class MQProducer {

    enum Producer {
        INSTANCE;
        private DefaultMQProducer producer;

        Producer() {
            producer = new DefaultMQProducer(MQEnums.GROUP.getValue());
            producer.setNamesrvAddr(MQEnums.NAME_SRV_ADDR.getValue());

            try {
                producer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public DefaultMQProducer getInstance() {
            return producer;
        }
    }

    /**
     * 发送同步消息
     * @param topic
     * @param tag
     * @param key
     * @param body
     * @return 是否发送成功
     */
    public static boolean sendSync(String topic, String tag, String key, String body) {
        Message msg;
        try {
            msg = new Message(topic, tag, key, body.getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = Producer.INSTANCE.getInstance().send(msg);
            if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 发送异步消息
     * @param topic
     * @param tag
     * @param key
     * @param body
     */
    public static void sendAsync(String topic, String tag, String key, String body) {
        Message msg;
        try {
            msg = new Message(topic, tag, key, body.getBytes(RemotingHelper.DEFAULT_CHARSET));

            Producer.INSTANCE.getInstance().send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功,消息topic:" + topic + ",tag:" + tag + ",key:" + key + ",body:" + body + "\n");
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败,消息topic:" + topic + ",tag:" + tag + ",key:" + key + ",body:" + body + "\n");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送顺序消息
     * @param topic
     * @param tag
     * @param key
     * @param body
     * @return 是否发送成功
     */
    public static boolean sendOrderly(String topic, String tag, String key, String body) {
        Message msg = null;
        try {
            msg = new Message(topic, tag, key, body.getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = Producer.INSTANCE.getInstance().send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long id = (long) o;
                    long index = id % list.size();
                    return list.get((int) index);
                }
            }, key);
            if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 发送OneWay消息
     * @param topic
     * @param tag
     * @param key
     * @param body
     */
    public static void sendOneWay(String topic, String tag, String key, String body) {
        Message msg = null;
        try {
            msg = new Message(topic, tag, key, body.getBytes(RemotingHelper.DEFAULT_CHARSET));

            Producer.INSTANCE.getInstance().sendOneway(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
