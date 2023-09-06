package com.xian.jdk.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/8/8 11:36
 * @Version: 1.0.0
 */
public class MsgpackV2 {


    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {

        MyClass myObject  = new MyClass();
        myObject.setAge(42);
        myObject.setName("Hello, MessagePack!");
        myObject.setAddress(Lists.newArrayList("a1", "a2"));
        HashMap<String, Object> input = new HashMap<>();
        input.put("i", 1);
        input.put("str", "abc");
        myObject.setInput(input);

        String str = "{\"name\":13,\"data\":{\"frameId\":50,\"input\":{\"type\":0,\"id\":8,\"direction\":{\"x\":-0.1573,\"y\":-0.9876},\"dt\":0.1679}}}";
        JSONObject jsonObject = JSONObject.parseObject(str);

        byte[] serializedData = serialize(jsonObject);
        for (byte serializedDatum : serializedData) {
            System.out.print((serializedDatum) + " ");
        }


        System.out.println(" ");

//        System.out.println("Serialized Data Length: " + serializedData.length);
//
        MyClass deserializedObject = deserialize(serializedData, MyClass.class);
        System.out.println(JSON.toJSONString(deserializedObject));
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static byte[] serialize(Object obj) throws IOException, IllegalAccessException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = field.get(obj);

            if (fieldValue instanceof Integer) {
                packer.packInt((Integer) fieldValue);
            }
            // 处理字符串
            else if (fieldValue instanceof String) {
                packer.packString((String) fieldValue);
            }
            // 处理List
            else if (fieldValue instanceof List) {
                List<String> listValue = (List<String>) fieldValue;
                packer.packArrayHeader(listValue.size());
                for (String item : listValue) {
                    packer.packString(item);
                }
            }
            // 处理HashMap
            else if (fieldValue instanceof HashMap) {
                HashMap<String, Object> mapValue = (HashMap<String, Object>) fieldValue;
                packer.packMapHeader(mapValue.size());
                for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
                    packer.packString(entry.getKey());
                    Object entryValue = entry.getValue();
                    if (entryValue instanceof Integer) {
                        packer.packInt((Integer) entryValue);
                    } else if (entryValue instanceof String) {
                        packer.packString((String) entryValue);
                    } // Add more cases for other types as needed
                }
            }
        }

        packer.close();
        return packer.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);

        T obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            // 整数
            if (field.getType().equals(Integer.TYPE)) {
                int intValue = unpacker.unpackInt();
                field.set(obj, intValue);
            }

            // 字符串
            else if (field.getType().equals(String.class)) {
                String stringValue = unpacker.unpackString();
                field.set(obj, stringValue);
            }

            // 列表
            else if (field.getType().equals(List.class)) {
                int arrayLength = unpacker.unpackArrayHeader();
                List<String> listValue = new ArrayList<>();
                for (int i = 0; i < arrayLength; i++) {
                    String itemValue = unpacker.unpackString();
                    listValue.add(itemValue);
                }
                field.set(obj, listValue);
            }
            // 反序列化
            else if (field.getType().equals(HashMap.class)) {
                int mapSize = unpacker.unpackMapHeader();
                HashMap<String, Object> mapValue = new HashMap<>();
                for (int i = 0; i < mapSize; i++) {
                    String key = unpacker.unpackString();
                    Object value = null;
                    if (unpacker.getNextFormat().getValueType().isIntegerType()) {
                        value = unpacker.unpackInt();
                    } else if (unpacker.getNextFormat().getValueType().isStringType()) {
                        value = unpacker.unpackString();
                    } // Add more cases for other types as needed
                    mapValue.put(key, value);
                }
                field.set(obj, mapValue);
            }


        }

        unpacker.close();
        return obj;
    }

}
