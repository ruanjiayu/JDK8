package com.xian.jdk.proto;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/7/11 14:07
 * @Version: 1.0.0
 */
public class OwnHashMapTest {

    public static void main(String[] args) {
        OwnHashMapProto.KeyValuePair.Builder keyValuePair = OwnHashMapProto.KeyValuePair.newBuilder();
        keyValuePair.setKey("name");

        String value = "xu";
        ByteString bytes = ByteString.copyFromUtf8(value);
        Any anyValue = Any.newBuilder()
                .setValue(bytes)
                .build();

        keyValuePair.setValue(anyValue);


        // 创建 OwnHashMapProto.Message 消息对象并设置字段值
        OwnHashMapProto.MyMessage.Builder messageBuilder = OwnHashMapProto.MyMessage.newBuilder();
        messageBuilder.addKeyValues(0, keyValuePair.build());

        OwnHashMapProto.MyMessage message = messageBuilder.build();

        // 序列化消息对象
        byte[] serializedMessage = message.toByteArray();

        // 反序列化消息对象
        try {
            OwnHashMapProto.MyMessage deserializedMessage = OwnHashMapProto.MyMessage.parseFrom(serializedMessage);

            // 验证字段值
            List<OwnHashMapProto.KeyValuePair> keyValues = deserializedMessage.getKeyValuesList();
            Map<String, Object> hashMap = new HashMap<>();

            for (OwnHashMapProto.KeyValuePair keyValue : keyValues) {
                String key = keyValue.getKey();
                hashMap.put(key, new String(keyValue.getValue().toByteArray()));
            }

            // 将 HashMap 转换为 JSON 格式
            System.out.println(hashMap);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

}
