package com.mq.workfair;

import com.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**公平分发
 * 其实是能者多劳
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
        //每次只发送一条消息
        channel.basicQos(1);
        for (int i = 0; i < 50 ; i++) {
            String msg = "hello :" + i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("send : " + msg);
            Thread.sleep(i*20);
        }
        channel.close();
        connection.close();
    }
}
