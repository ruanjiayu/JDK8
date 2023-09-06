package com.xian.jdk.mqtt;

import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2022/12/14 15:29
 * @Version: 1.0.0
 */
public class MqttPublishSample {

    public static void main(String[] args) {

        String broker = "tcp://47.110.151.225:11883";
        String topic = "dev/horizontalBar/dataUp/DC5475D13110";
        String username = "test_skipRope";
        String password = "ZHVvZ3VhbkBlbXF4";
        String clientid = "publish_client";

        String msg = "{\"method\":\"event_post\",\"event_id\":\"run_state\",\"device_id\":\"DC5475D13110\",\"startup_index\":45,\"params\":{\"battery_voltage\":3.7200000286102295,\"battery_step\":1,\"infrared_sensor\":0,\"infrared_reset\":0,\"level_sensor\":1,\"level_angle\":-6.3903746604919434,\"press_sensor1\":0,\"press_sensor2\":0}}";

        int qos = 0;

        try {
            MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置用户名和密码
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setKeepAliveInterval(60);
            options.setConnectionTimeout(60);
            // 连接
            client.connect(options);

            JSONObject jsonObject = JSONObject.parseObject(msg);
            for (int i = 0; i < 20; i++) {
                jsonObject.getJSONObject("params").put("infrared_sensor", i);
                // 创建消息并设置 QoS
                MqttMessage message = new MqttMessage(jsonObject.toJSONString().getBytes());
                message.setQos(qos);
                // 发布消息
                client.publish(topic, message);
            }

            System.out.println("发送成功");


            // 断开连接
            client.disconnect();
            // 关闭客户端
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

    }
}
