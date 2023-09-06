package com.xian.jdk.msg;

import com.alibaba.fastjson.JSON;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/8/10 11:52
 * @Version: 1.0.0
 */
public class MsgPack3 {

    public static void main(String[] args) throws IOException {
        String jsonData = "{\"name\":\"阮\",\"data\":{\"frameId\":-50,\"input\":{\"type\":0,\"id\":8,\"direction\":{\"x\":-0.1573,\"y\":-0.9876},\"dt\":0.1679}}}";

        Map<String, Object> jsonMap = convertJsonToMap(jsonData);
        byte[] messagePackData = serializeMapToMessagePack(jsonMap);
        printMessagePackData(messagePackData);

        Map<String, Object> deserializedMap = deserializeMessagePackData(messagePackData);
        String json = convertMapToJson(deserializedMap);
        System.out.println("JSON Data:");
        System.out.println(json);
    }

    private static void printMessagePackData(byte[] data) {
        System.out.println("MessagePack Data:");
        for (byte b : data) {
            System.out.print(String.format("%02X ", b));
        }
        System.out.println();
    }

    private static String convertMapToJson(Map<String, Object> map) {
        return JSON.toJSONString(map);
    }

    private static Map<String, Object> convertJsonToMap(String json) {
        return JSON.parseObject(json, Map.class);
    }

    private static byte[] serializeMapToMessagePack(Map<String, Object> map) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        packer.packMapHeader(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            packer.packString(entry.getKey());
            Object value = entry.getValue();
            if (value instanceof Integer) {
                packer.packInt((Integer) value);
            } else if (value instanceof Double) {
                packer.packDouble((Double) value);
            } else if (value instanceof Map) {
                serializeMap(packer, (Map<String, Object>) value);
            } // Add more cases for other types as needed
        }
        packer.close();
        return packer.toByteArray();
    }



    private static Map<String, Object> deserializeMessagePackData(byte[] data) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
        return deserializeMap(unpacker);
    }




    private static void serializeValue(MessageBufferPacker packer, Object value) throws IOException {
        if (value instanceof Integer) {
            packer.packInt((Integer) value);
        } else if (value instanceof Double) {
            packer.packDouble((Double) value);
        } else if (value instanceof String) {
            packer.packString((String) value);
        } else if (value instanceof Map) {
            serializeMap(packer, (Map<?, ?>) value);
        }
        // Add more cases for other types as needed
    }

    private static void serializeMap(MessageBufferPacker packer, Map<?, ?> map) throws IOException {
        packer.packMapHeader(map.size());
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            serializeValue(packer, entry.getKey());
            serializeValue(packer, entry.getValue());
        }
    }


    private static Map<String, Object> deserializeMap(MessageUnpacker unpacker) throws IOException {
        int mapSize = unpacker.unpackMapHeader();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            String key = unpacker.unpackString();
            System.out.println("Key: " + key); // Add this line for debugging

            Object value = deserializeValue(unpacker);
            map.put(key, value);
        }
        return map;
    }

    private static Object deserializeValue(MessageUnpacker unpacker) throws IOException {
        switch (unpacker.getNextFormat().getValueType()) {
            case STRING:
                return unpacker.unpackString();
            case INTEGER:
                return unpacker.unpackInt();
            case FLOAT:
                return unpacker.unpackDouble();
            case MAP:
                return deserializeMap(unpacker);
            // Add more cases for other types as needed
            default:
                return null;
        }
    }







}
