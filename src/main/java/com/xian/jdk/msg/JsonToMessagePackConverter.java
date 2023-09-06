package com.xian.jdk.msg;

import com.alibaba.fastjson.JSON;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonToMessagePackConverter {

    public static void main(String[] args) throws IOException {
        String jsonData = "{\"inputs\":[{\"type\":1,\"dt\":0.1086},{\"type\":2,\"dt\":0.1086}]}";

        // Convert JSON string to Map using Fastjson
        Map<String, Object> jsonMap = JSON.parseObject(jsonData);

        // Serialize Map to MessagePack format
        byte[] messagePackData = serializeMapToMessagePack(jsonMap);

        // Deserialize MessagePack data to Map
        Map<String, Object> messagePackMap = deserializeMessagePackData(messagePackData);

        // Convert Map to JSON string using Fastjson
        String json = JSON.toJSONString(messagePackMap);
        System.out.println("JSON Data:");
        System.out.println(json);
    }

    private static byte[] serializeMapToMessagePack(Map<String, Object> map) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        serializeMap(packer, map);
        packer.close();
        return packer.toByteArray();
    }

    private static void serializeMap(MessageBufferPacker packer, Map<String, Object> map) throws IOException {
        packer.packMapHeader(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            packer.packString(entry.getKey());
            Object value = entry.getValue();
            serializeValue(packer, value);
        }
    }

    private static void serializeList(MessageBufferPacker packer, List<Object> list) throws IOException {
        packer.packArrayHeader(list.size());
        for (Object item : list) {
            serializeValue(packer, item);
        }
    }

    private static void serializeValue(MessageBufferPacker packer, Object value) throws IOException {
        if (value instanceof Integer) {
            packer.packInt((Integer) value);
        } else if (value instanceof Double) {
            packer.packDouble((Double) value);
        } else if (value instanceof String) {
            packer.packString((String) value);
        } else if (value instanceof Map) {
            serializeMap(packer, (Map<String, Object>) value);
        } else if (value instanceof List) {
            serializeList(packer, (List<Object>) value);
        }
    }

    private static Map<String, Object> deserializeMessagePackData(byte[] data) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
        return deserializeMap(unpacker);
    }

    private static Map<String, Object> deserializeMap(MessageUnpacker unpacker) throws IOException {
        int mapSize = unpacker.unpackMapHeader();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            String key = unpacker.unpackString();
            Object value = deserializeValue(unpacker);
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> deserializeList(MessageUnpacker unpacker) throws IOException {
        int arraySize = unpacker.unpackArrayHeader();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            Object value = deserializeValue(unpacker);
            list.add(value);
        }
        return list;
    }

    private static Object deserializeValue(MessageUnpacker unpacker) throws IOException {
        if (unpacker.getNextFormat().getValueType().isIntegerType()) {
            return unpacker.unpackInt();
        } else if (unpacker.getNextFormat().getValueType().isFloatType()) {
            return unpacker.unpackDouble();
        } else if (unpacker.getNextFormat().getValueType().isStringType()) {
            return unpacker.unpackString();
        } else if (unpacker.getNextFormat().getValueType().isArrayType()) {
            return deserializeList(unpacker);
        } else if (unpacker.getNextFormat().getValueType().isMapType()) {
            return deserializeMap(unpacker);
        }
        return null;
    }
}
