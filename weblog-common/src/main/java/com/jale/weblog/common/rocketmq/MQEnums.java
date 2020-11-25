package com.jale.weblog.common.rocketmq;

/**
 * RocketMQ相关属性枚举
 */
public enum MQEnums {

    /**
     * 生产/消费组名
     */
    GROUP("commonGroup"),
    /**
     * nameserver地址
     */
    NAME_SRV_ADDR("8.129.114.212:9876"),
    /**
     * 主题Topic
     */
    TOPIC("weblog"),
    /**
     * 事务提供者/消费组
     */
    TRANS_GROUP("transGroup"),
    /**
     * 事务topic
     */
    TX_TOPIC("txTopic")
    ;

    private String value;

    MQEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
