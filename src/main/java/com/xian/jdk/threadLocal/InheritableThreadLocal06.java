package com.xian.jdk.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 主线程修改值，子线程无法同步到。
 * 当我们在new Thread()时，会有从父线程拷贝线程本地变量到子线程的代码。
 * 而我们使用了Executors.newFixedThreadPool(1),保证了只有一个线程创建，所以我们在主线程修改自线程无法同步到。
 * 但是Executors.newFixedThreadPool(2)，那么第二个任务就是新的子线程，可以拷贝到最新父线程的值。
 * @Author: Summer
 * @DateTime: 2021/11/1 4:58 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class InheritableThreadLocal06 {

    public static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开启");
        threadLocal.set(1);

        executorService.submit(() -> System.out.println("子线程读取本地变量：" + threadLocal.get()));

        TimeUnit.SECONDS.sleep(1);

        threadLocal.set(2);

        executorService.submit(() -> System.out.println("子线程读取本地变量：" + threadLocal.get()));
    }
}
