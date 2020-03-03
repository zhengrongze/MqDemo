package com.mq.pb;

import com.mq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecvSMS {
    public static final String QUEUE_NAME = "test_queue_fanout_sms";
    public static final String EXCHANGE_NAME ="test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //队列绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
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