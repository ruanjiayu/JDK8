package com.xian.jdk.msg;

import com.alibaba.fastjson.JSON;
import com.sun.tools.javac.util.List;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/8/8 11:36
 * @Version: 1.0.0
 */
public class Msgpack {


    public static void main(String[] args) throws IOException {

        // 创建一个MessagePacker实例
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        // 创建一个MyClass对象
        MyClass myObject = new MyClass(42, "Hello, MessagePack!", List.of("Address1", "Address2"));

        // 将对象数据写入MessagePacker
        packer.packInt(myObject.getAge())
                .packString(myObject.getName());

        // 获取序列化后的字节数组
        byte[] serializedData = packer.toByteArray();

        // 计算字节长度
        System.out.println(JSON.toJSONString(myObject).length());
        System.out.println(serializedData.length);

        // 关闭MessagePacker
        packer.close();

        // 现在你可以将serializedData发送到其他地方

        // 反序列化部分
        // 创建一个MessageUnpacker实例
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(serializedData);

        // 从MessageUnpacker中读取数据并创建MyClass对象
        int intValue = unpacker.unpackInt();
        String stringValue = unpacker.unpackString();


        // 关闭MessageUnpacker
        unpacker.close();

        // 现在你可以使用deserializedObject，它是反序列化后的对象
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
            } else if (fieldValue instanceof String) {
                packer.packString((String) fieldValue);
            } else if (fieldValue instanceof List) {
                List<String> listValue = (List<String>) fieldValue;
                packer.packArrayHeader(listValue.size());
                for (String item : listValue) {
                    packer.packString(item);
                }
            }
        }

        packer.close();
        return packer.toByteArray();
    }

}
