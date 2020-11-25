package com.jale.weblog.user.service.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashMap;
import java.util.Map;

public class TransactionListenerImpl implements TransactionListener {

    private Map<String, LocalTransactionState> stateMap = new HashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            System.out.println("事务Half消息发送成功，开始执行user业务逻辑" + JSON.toJSONString(message) + "，参数：" + o);

            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String transId = messageExt.getTransactionId();
        System.out.println("回查消息--->transId = " + transId + ", state = " + stateMap.get(transId));

        //return stateMap.get(transId);
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
