package com.xian.jdk.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 正则表达式
 * @Author: Summer
 * @DateTime: 2021/12/1 3:00 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class RegexTest {


    /**
     * 匹配链接内是否存在URL
     */
    @Test
    public void test1() {
        String str = "阮佳裕最喜欢的链接http://www.baidu.com哈哈哈哈哈你好";
        String regex = "(https?|http?)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.groupCount());
        }
    }

    /**
     * 匹配文字中的链接
     */
    @Test
    public void test2() {
        String str = "阮佳裕京东https://item.jd.com/23247384728哈哈哈哈，我很喜欢";
        String regex = "https://(u.jd|item.jd|item.m.jd).com/[a-zA-Z0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            System.out.println(matcher.groupCount());
        }
    }



    /**
     * 匹配文字中的链接
     */
    @Test
    public void test3() {
        String str = "阮佳裕京东https://detail.tmall.com?id=23247384728哈哈哈哈，我很喜欢";
        String regex = "detail.tmall.*(\\?id|\\&id)=([0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(matcher.groupCount()));
        }
    }
}
