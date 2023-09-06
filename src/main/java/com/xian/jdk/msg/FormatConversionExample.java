package com.xian.jdk.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ValueType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormatConversionExample {
    public static void main(String[] args) throws IOException {
        String jsonData = "{\"data\":{\"inputs\":[{\"dt\":0.1086,\"type\":2},{\"dt\":0.1174,\"id\":8,\"type\":0,\"direction\":{\"x\":-1,\"y\":0}},{\"dt\":0.1086,\"type\":2},{\"dt\":0.1086,\"type\":2}],\"lastFrameId\":18},\"name\":14}";

        Map<String, Object> jsonMap = JSON.parseObject(jsonData, new TypeReference<Map<String, Object>>() {
        });
        ;

        byte[] messagePackData = serializeMapToMessagePack(jsonMap);

        int jsonLength = jsonData.getBytes().length;
        int packLength = messagePackData.length;
        System.out.println("压缩前的长度:" + jsonLength);
        System.out.println("压缩后的长度:" + packLength);
        System.out.println("压缩比" + ((double) (jsonLength - packLength) / jsonLength) * 100);

        System.out.println("MessagePack Data:");
//        for (byte b : messagePackData) {
//            System.out.print(String.format("%02X ", b));
//        }

        System.out.println();

        for (byte b : messagePackData) {
            System.out.print(b + " ");
        }

        System.out.println();


        Map<String, Object> messagePackMap = deserializeMessagePackData(messagePackData);


        String json = convertMapToJson(messagePackMap);

        System.out.println("JSON Data:");
        System.out.println(json);
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
            } else if (value instanceof BigDecimal) {
                packer.packDouble(((BigDecimal) value).doubleValue());
            } else if (value instanceof String) {
                packer.packString((String) value);
            } else if (value instanceof Map) {
                serializeMap(packer, (Map<String, Object>) value);
            } else if (value instanceof JSONArray) {
                List<Map> maps = ((JSONArray) value).toJavaList(Map.class);
                packer.packArrayHeader(maps.size());
                for (Map m : maps) {
                    serializeMap(packer, m);
                }
            }
        }
        packer.close();
        return packer.toByteArray();
    }

    private static void serializeMap(MessageBufferPacker packer, Map<String, Object> map) throws IOException {
        packer.packMapHeader(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            packer.packString(entry.getKey());
            Object value = entry.getValue();

            if (value instanceof Integer) {
                packer.packInt((Integer) value);
            } else if (value instanceof Double) {
                packer.packDouble((Double) value);
            } else if (value instanceof BigDecimal) {
                packer.packDouble(((BigDecimal) value).doubleValue());
            } else if (value instanceof String) {
                packer.packString((String) value);
            } else if (value instanceof Map) {
                serializeMap(packer, (Map<String, Object>) value);
            } else if (value instanceof JSONArray) {
                List<Map> maps = ((JSONArray) value).toJavaList(Map.class);
                packer.packArrayHeader(maps.size());
                for (Map m : maps) {
                    serializeMap(packer, m);
                }
            }
        }
    }


    private static Map<String, Object> deserializeMessagePackData(byte[] data) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
        return (Map<String, Object>) deserializeValue(unpacker);
    }

    private static Map<String, Object> deserializeMap(MessageUnpacker unpacker) throws IOException {
        int mapSize = unpacker.unpackMapHeader();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            String key = unpacker.unpackString();
            map.put(key, deserializeValue(unpacker));
        }
        return map;
    }

    private static List<Object> deserializeList(MessageUnpacker unpacker) throws IOException {
        int arraySize = unpacker.unpackArrayHeader();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            list.add(deserializeValue(unpacker));
        }
        return list;
    }

    private static Object deserializeValue(MessageUnpacker unpacker) throws IOException {
        ValueType valueType = unpacker.getNextFormat().getValueType();
        if (valueType.isMapType()) {
            return deserializeMap(unpacker);
        } else if (valueType.isArrayType()) {
            return deserializeList(unpacker);
        } else if (valueType.isIntegerType()) {
            return unpacker.unpackInt();
        } else if (valueType.isFloatType()) {
            return unpacker.unpackDouble();
        } else if (valueType.isStringType()) {
            return unpacker.unpackString();
        }
        return null;
    }

    private static String convertMapToJson(Map<String, Object> map) {
        return JSON.toJSONString(map);
    }
}
