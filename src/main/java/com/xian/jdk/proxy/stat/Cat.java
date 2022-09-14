package com.xian.jdk.proxy.stat;

/**
 * @Description:
 * @Author: Summer
 * @DateTime: 2022/7/11 5:34 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class Cat implements Animal{


    @Override
    public void call() {
        System.out.println("喵喵喵 ~");
    }
}
