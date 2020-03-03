package com.mq.routing;

import com.mq.util.ConnectionUtil;
import com.mq.util.ExchangeEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 */
public class Send {
    public static final String EXCHANE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANE_NAME,ExchangeEnum.direct.getType());
        //绑定error这个列表
        String routingKey ="error";
        String msg = "hello " + routingKey;
        channel.basicPublish(EXCHANE_NAME,routingKey,null,msg.getBytes());
        System.out.println("send msg = " + msg);
        channel.close();
        connection.close();
    }
}
