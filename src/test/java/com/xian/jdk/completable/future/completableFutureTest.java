package com.xian.jdk.completable.future;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2022/11/19 16:47
 * @Version: 1.0.0
 */
public class completableFutureTest {


    /**
     * 带返回值异步请求，默认线程池
     * get 等待任务执行完成
     */
    @Test
    public void supplyAsyncTest() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("do something....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "结果";
        });

        //等待任务执行完成
//        System.out.println("结果->" + cf.get(1, TimeUnit.SECONDS));
//        System.out.println("结果->" + cf.get());
        System.out.println("结果->" + cf.join());
        System.out.println("结果->" + cf.getNow("默认值哦"));
        System.out.println("结果->" + cf.complete("任务没有完成、返回此值"));
        System.out.println("结果->" + cf.completeExceptionally(new TimeoutException()));

    }


    /**
     * 不带返回值的异步请求
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void runAsyncTest() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("do something....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //等待任务执行完成
        System.out.println("结果->" + cf.get());
    }

    @Test
    public void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(2000);
                int a = 1 / 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 任务完成");
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 任务完成");
            return "cf2 任务完成";
        });

        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf3 任务完成");
            return "cf3 任务完成";
        });

        CompletableFuture<Object> cfAll = CompletableFuture.anyOf(cf1, cf2, cf3);
//        System.out.println("cfAll结果->" + cfAll.get());
        Thread.sleep(3000);

    }










    /**
     * 带返回值异步请求，使用自己定义的线程池操作
     * 使用自己定义的线程池
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void supplyAsyncTest2() throws ExecutionException, InterruptedException {
        // 自定义线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("do something....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result";
        }, executorService);

        //等待子任务执行完成
        System.out.println("结果->" + cf.get());
    }

}
