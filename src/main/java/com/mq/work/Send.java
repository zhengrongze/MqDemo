package com.mq.work;

import com.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 轮询分发，不会因为谁快谁慢而消息处理数量不同
 */
public class Send {
    /**                 \--c1
     * P-------queue ---
     *                  \--c2
     */
    private static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i < 50 ; i++) {
            String msg = "hello :" + i;
            //exchange,为匿名交换机
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("send : " + msg);
            Thread.sleep(i*20);
        }
        channel.close();
        connection.close();
    }
}
