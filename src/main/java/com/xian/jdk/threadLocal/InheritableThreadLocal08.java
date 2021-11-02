package com.xian.jdk.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 进行了值传递(引用传递），注意线程安全问题。
 * 主线程和自线程都会互相影响
 * @Author: Summer
 * @DateTime: 2021/11/2 5:24 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class InheritableThreadLocal08 {
    public static ThreadLocal<Stu> threadLocal = new InheritableThreadLocal<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开启");
        threadLocal.set(new Stu("aaa",1));

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
            threadLocal.get().setAge(55);
            System.out.println("子线程读取本地变量：" + threadLocal.get());

        });

        TimeUnit.SECONDS.sleep(1);

        System.out.println("主线程读取本地变量：" + threadLocal.get());
        threadLocal.get().setAge(99);

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });
    }

   public static class Stu{

        private String name;

        private Integer age;

       public Stu(String name, Integer age) {
           this.name = name;
           this.age = age;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public Integer getAge() {
           return age;
       }

       public void setAge(Integer age) {
           this.age = age;
       }

       @Override
       public String toString() {
           return "Stu{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   '}';
       }
   }
}
