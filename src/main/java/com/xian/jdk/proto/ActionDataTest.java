package com.xian.jdk.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.Arrays;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/7/12 10:02
 * @Version: 1.0.0
 */
public class ActionDataTest {

    public static void main(String[] args) {

        //初始化数据
        ActionDataProto.ActionData.Builder demo = ActionDataProto.ActionData.newBuilder();
        demo.setType("daPao")
                .build();

        //序列化
        ActionDataProto.ActionData build = demo.build();
        //转换成字节数组
        byte[] s = build.toByteArray();
        System.out.println("protobuf数据bytes[]:" + Arrays.toString(s));
        System.out.println("protobuf序列化大小: " + s.length);

        ActionDataProto.ActionData demo1;
        String jsonObject = null;
        try {
            //反序列化
            demo1 = ActionDataProto.ActionData.parseFrom(s);

            System.out.println(demo1.getType());

            ActionDataV2Proto.ActionDataV2 dataV2 = ActionDataV2Proto.ActionDataV2.parseFrom(s);
            String jsonObjStr = JsonFormat.printer().print(dataV2);
            System.out.println(jsonObjStr);
//            //转 json
//            jsonObject = JsonFormat.printer().print(demo1);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        System.out.println("Json格式化结果:\n" + jsonObject);
        System.out.println("Json格式化数据大小: " + jsonObject.getBytes().length);
    }

}
