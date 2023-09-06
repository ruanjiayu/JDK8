package com.xian.jdk.json;

import com.alibaba.fastjson.JSON;

/**
 * @Description: JsonField是位于fastjson包下的
 * @Author: summer
 * @CreateDate: 2022/12/20 16:31
 * @Version: 1.0.0
 */
public class JsonFieldTest {

    public static void main(String[] args) {
        String str = "{\"name\":\"张name\"}";
        String str2 = "{\"finalName\":\"张finalName\"}";
        Person person1 = JSON.parseObject(str, Person.class);
        Person person2 = JSON.parseObject(str2, Person.class);
        System.out.println(JSON.toJSONString(person1));
        System.out.println(JSON.toJSONString(person2));

    }

}
