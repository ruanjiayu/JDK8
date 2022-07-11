package com.xian.jdk.jvm;

import java.lang.ref.WeakReference;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Description: -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:+UseG1GC
 * @Author: Summer
 * @DateTime: 2022/4/14 11:17 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class GCLogTest {

//    private static final int S_1MB = 256 * 1024;

    public static void main(String[] args) throws InterruptedException {

        new GCLogTest().fun();
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);
        Thread.sleep(1000);

    }

    public void fun() throws InterruptedException {
        ThreadLocal<Double> t = new ThreadLocal<>();
        t.set(12d);
        System.out.println(t.get());
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(t.get());
    }


}
