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
        String str = "24480408949-D-11";
        String regex = "[\\w-,]+";
        Pattern pattern = Pattern.compile(regex);
        System.out.println(Pattern.matches(regex, str));
//        Matcher matcher = pattern.matcher(str);
//        if (matcher.find()) {
//            System.out.println(matcher.group());
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
//            System.out.println(matcher.groupCount());
//            System.out.println(matcher.group(matcher.groupCount()));
//        }
//        System.out.println("find() -> " + matcher.find());
//        System.out.println("find() -> " + matcher.find());
    }
}
