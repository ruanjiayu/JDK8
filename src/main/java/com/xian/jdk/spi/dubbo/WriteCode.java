package com.xian.jdk.spi.dubbo;

/**
 * @Description:
 * @Author: Summer
 * @DateTime: 2022/7/8 10:43 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class WriteCode implements DoWork {

    @Override
    public void doWork() {
        System.out.println("写code");
    }
}
