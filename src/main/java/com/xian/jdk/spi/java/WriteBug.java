package com.xian.jdk.spi.java;

/**
 * @Description:
 * @Author: Summer
 * @DateTime: 2022/7/8 10:43 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class WriteBug implements DoWork {
    @Override
    public void doWork() {
        System.out.println("写bug");
    }
}
