package com.xian.jdk.threadLocal;

/**
 * @Description: InheritableThreadLocal 实现了父子线程之间的数据同步
 * 当我们在new Thread()时，会有从父线程拷贝线程本地变量到子线程的代码
 * @Author: Summer
 * @DateTime: 2021/11/1 4:52 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class InheritableThreadLocal05 {

    public static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws Exception {
        threadLocal.set(12345);
        Thread thread = new MyThread();
        thread.start();
        System.out.println("main = " + threadLocal.get());
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread = " + threadLocal.get());
        }
    }

}
