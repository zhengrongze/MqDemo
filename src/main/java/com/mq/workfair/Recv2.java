package com.mq.workfair;

import com.mq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        //消息的持久化durable,小细节，已经声明好的队列不能修改它的durable
        //设置为true之后的消息去哪里查询呢
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("recv[2] = "+ msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("recv[2] done");
                    // 返回确认状态
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //aotoAck = true 自动确认模式，一旦mq将信息分发给消费者，就会从内存中删除
        //一旦消费者挂了，就会导致数据丢失
        // 监听队列，手动返回完成
        boolean aotoAck = false;//消息应答改为false
        channel.basicConsume(QUEUE_NAME,aotoAck,defaultConsumer);
    }
}
