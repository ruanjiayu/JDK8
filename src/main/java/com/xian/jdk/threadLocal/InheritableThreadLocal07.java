package com.xian.jdk.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 子线程中的清除对父线程无影响
 * @Author: Summer
 * @DateTime: 2021/11/2 5:03 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class InheritableThreadLocal07 {
    public static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开启");
        threadLocal.set(1);

        executorService.submit(() -> {
            // 1
            System.out.println("子线程读取本地变量：" + threadLocal.get());
            // 清除线程中的值
            threadLocal.remove();
        });

        TimeUnit.SECONDS.sleep(1);

        threadLocal.set(2);

        executorService.submit(() -> {
            // null
            System.out.println("子线程读取本地变量：" + threadLocal.get());
            threadLocal.set(3);
            // 3
            System.out.println("子线程读取本地变量：" + threadLocal.get());
            threadLocal.remove();
        });

    }
}
