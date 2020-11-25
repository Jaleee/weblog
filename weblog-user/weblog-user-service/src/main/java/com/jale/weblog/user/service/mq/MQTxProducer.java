package com.jale.weblog.user.service.mq;

import com.jale.weblog.common.rocketmq.MQEnums;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.*;

public class MQTxProducer {

    enum Producer {
        INSTANCE;
        private TransactionMQProducer producer;

        Producer() {
            TransactionListener transactionListener = new TransactionListenerImpl();
            producer = new TransactionMQProducer(MQEnums.TRANS_GROUP.getValue());
            producer.setNamesrvAddr(MQEnums.NAME_SRV_ADDR.getValue());
            ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("client-transaction-msg-check-thread");
                    return thread;
                }
            });
            producer.setExecutorService(executorService);
            producer.setTransactionListener(transactionListener);
            try {
                producer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public TransactionMQProducer getInstance() {
            return producer;
        }
    }

    public static boolean sendTx(String topic, String tag, String key, String body) {
        Message msg;
        try {
            msg = new Message(topic, tag, key, body.getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = MQTxProducer.Producer.INSTANCE.getInstance().sendMessageInTransaction(msg, null);
            if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
