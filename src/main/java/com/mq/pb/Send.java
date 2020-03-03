package com.mq.pb;

import com.mq.util.ConnectionUtil;
import com.mq.util.ExchangeEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式
 */
public class Send {
    public static final String EXCHANGE_NAME ="test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //一般来说direct和topic用来具体的路由消息，如果要用广播的消息一般用fanout的exchange。
        //
        //header类型用的比较少，但还是知道一点好
        channel.exchangeDeclare(EXCHANGE_NAME,ExchangeEnum.fanout.getType());//type分发 类型
        String msg = "hello fanout";
        //消息只会存储在队列当中，所以这个交换机上是没有消息的
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("send:" + msg);
        channel.close();
        connection.close();
    }
}
