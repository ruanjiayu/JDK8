package com.xian.jdk.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.util.Arrays;

/**
 * @author Administrator
 * @version 1.0.0
 * @ProjectName com.qingfeng-client
 * @Description TODO
 * @createTime 2022年05月01日 18:10:00
 */
public class DemoTest {

    public static void main(String[] args) {

        //初始化数据
        DemoProto.Demo.Builder demo = DemoProto.Demo.newBuilder();
        demo.setId(4)
                .setCode("王武")
                .setName("张三")
                .build();

        //序列化
        DemoProto.Demo build = demo.build();
        //转换成字节数组
        byte[] s = build.toByteArray();
        System.out.println("protobuf数据bytes[]:" + Arrays.toString(s));
        System.out.println("protobuf序列化大小: " + s.length);

        System.out.println(new String(s));

        DemoProto.Demo demo1;
        String jsonObject = null;
        try {
            //反序列化
            demo1 = DemoProto.Demo.parseFrom(s);
            //转 json
            jsonObject = JsonFormat.printer().print(demo1);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        System.out.println("Json格式化结果:\n" + jsonObject);
        System.out.println("Json格式化数据大小: " + jsonObject.getBytes().length);
    }

}
