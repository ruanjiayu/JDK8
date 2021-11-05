package com.xian.jdk.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: https://zhuanlan.zhihu.com/p/142846161
 * @Author: Summer
 * @DateTime: 2021/11/5 2:08 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class Test {

    public static void main(String[] args) {
        String str = "hello";
        String regex = "hello";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        System.out.println("find() -> " + matcher.find());
        System.out.println("find() -> " + matcher.find());
    }
}
