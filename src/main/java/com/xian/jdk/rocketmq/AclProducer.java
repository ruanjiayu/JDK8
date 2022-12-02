package com.xian.jdk.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;


/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2022/12/1 10:17
 * @Version: 1.0.0
 */
public class AclProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test");
        producer.setNamesrvAddr("47.110.151.225:13306");
        producer.setVipChannelEnabled(false);
        producer.start();

        // topic 和body
        Message msg = new Message("test", "阮佳裕".getBytes(StandardCharsets.UTF_8));
        SendResult send = producer.send(msg);

        // 关闭生产者
        producer.shutdown();
    }
}
