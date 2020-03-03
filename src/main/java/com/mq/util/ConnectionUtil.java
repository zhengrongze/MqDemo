package com.mq.util;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        //在控制台添加，相当于一个数据库
        factory.setVirtualHost("vhost_mmr");
        factory.setUsername("zrz");
        factory.setPassword("123456");
        return factory.newConnection();
    }
}
