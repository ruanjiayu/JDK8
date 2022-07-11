package com.xian.jdk.threadLocal;

/**
 * @Description:
 * @Author: Summer
 * @DateTime: 2022/4/18 4:44 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class ThreadLocalTest {

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        new ThreadLocalTest().funA();
        System.gc();
        Thread.sleep(3000);
    }

    public void funA() {
//        ThreadLocal<Integer> threadLocal = new ThreadLocal<>()
        // 创建一个线程本地变量
        threadLocal.set(100);
        threadLocal.get();
        // 函数末尾

    }


}
