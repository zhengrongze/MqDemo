package com.mq.topic;

import com.mq.util.ConnectionUtil;
import com.mq.util.ExchangeEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //定义exchange的类型
        channel.exchangeDeclare(EXCHANGE_NAME,ExchangeEnum.topic.getType());
        String msg = "Goods ...操作";
        channel.basicPublish(EXCHANGE_NAME,"goods.delete",null,msg.getBytes());
        System.out.println("send msg = " + msg);
        channel.close();
        connection.close();
    }
}
