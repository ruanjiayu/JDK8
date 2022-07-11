package com.xian.jdk.spi.java;

import java.util.ServiceLoader;

/**
 * @Description:
 * @Author: Summer
 * @DateTime: 2022/7/8 10:46 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        for ( ; ; ) {
            //获取到DoWork的所有实现
            ServiceLoader<DoWork> serviceServiceLoader = ServiceLoader.load(DoWork.class);
            for (DoWork doWork : serviceServiceLoader){
                doWork.doWork();
            }
            Thread.sleep(3000);
        }
    }
}
