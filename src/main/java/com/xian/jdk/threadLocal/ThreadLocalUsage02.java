package com.xian.jdk.threadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 会出现问题，SimpleDateFormat是线程不安全的
 * @Author: Summer
 * @DateTime: 2020/7/20 10:25 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class ThreadLocalUsage02 {
    public static ExecutorService THREAD_POOL = new ThreadPoolExecutor(10, 15, 1000L, TimeUnit.SECONDS, new ArrayBlockingQueue(1024), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            THREAD_POOL.submit(() -> {
                String date = new ThreadLocalUsage02().date(finalI);
                System.out.println(date);
            });
        }
        // 关闭线程池，此种关闭方式不再接受新的任务提交，等待现有队列中的任务全部执行完毕之后关闭
        THREAD_POOL.shutdown();
    }

    private String date(int seconds) {
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(1000 * seconds);
        return DATE_FORMAT.format(date);
    }

}
