package com.xian.jdk.threadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 这样不会出现问题，因为每一个线程自己内部创建了SimpleDateFormat
 * @Author: Summer
 * @DateTime: 2020/7/20 10:23 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class ThreadLocalUsage01 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalUsage01().date(10);
                System.out.println(date);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalUsage01().date(1000);
                System.out.println(date);
            }
        }).start();
    }

    private String date(int seconds) {
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
