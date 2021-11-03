package com.xian.jdk.threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 阿里巴巴的线程池数据传输
 * @Author: Summer
 * @DateTime: 2021/11/3 10:29 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class TransmittableThreadLocal01 {

    public static ThreadLocal<Integer> threadLocal = new TransmittableThreadLocal<>();
    public static ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开启");
        threadLocal.set(1);
        // 1
        System.out.println("主线程读取本地变量：" + threadLocal.get());

        executorService.submit(() -> {
            // 1
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });

        TimeUnit.SECONDS.sleep(1);

        threadLocal.set(2);
        // 2
        System.out.println("主线程读取本地变量：" + threadLocal.get());

        executorService.submit(() -> {
            //[读到了主线程修改后的新值] 2
            System.out.println("子线程读取本地变量：" + threadLocal.get());
            threadLocal.set(3);
            // 3
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });

        TimeUnit.SECONDS.sleep(1);
        //依旧读取的是 2
        System.out.println("主线程读取本地变量：" + threadLocal.get());
    }
}
