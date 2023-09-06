package com.xian.jdk.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2022/12/14 15:41
 * @Version: 1.0.0
 */
public class SubscribeSample {

    private static int num = 0;

    public static void main(String[] args) {

//        String broker = "tcp://mqtt.duoguan.cc:28083";
        String broker = "tcp://47.110.151.225:11883";
//        String topic = "common/skipRope/dataUp";
        String topic = "app/game/dataUp/1";
        String username = "test_skipRope";
        String password = "ZHVvZ3VhbkBlbXF4";
        String clientid = "subscribe_rianJiaYu";
        int qos = 0;

        try {
            MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
            // 设置连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
//            options.setConnectionTimeout(5);
            options.setKeepAliveInterval(5);
            options.setAutomaticReconnect(false);
            // 设置回调
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws InterruptedException, MqttException {
//                    System.out.println("topic: " + topic);
//                    System.out.println("Qos: " + message.getQos());
                    String content = new String(message.getPayload());
                    System.out.println("message content: " + content);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(options);
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
