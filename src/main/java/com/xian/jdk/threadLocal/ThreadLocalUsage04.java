package com.xian.jdk.threadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 使用ThreadLocal来规避掉线程不安全
 * @Author: Summer
 * @DateTime: 2020/7/20 10:44 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class ThreadLocalUsage04 {
    public static ExecutorService THREAD_POOL = new ThreadPoolExecutor(10, 15, 300, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            THREAD_POOL.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalUsage04().date(finalI);
                    System.out.println(date);
                }
            });
        }
        THREAD_POOL.shutdown();
    }

    private String date(int seconds) {
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = ThreadSafeDateFormatter.dateFormatThreadLocal.get();
        return simpleDateFormat.format(date);
    }

}

class ThreadSafeDateFormatter {

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
}
