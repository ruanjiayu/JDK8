package com.xian.jdk.threadLocal;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 对象值的复制。关键在于重写InheritableThreadLocal中的childValue的方法--->序列化反序列化
 * @Author: Summer
 * @DateTime: 2021/11/2 5:34 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class InheritableThreadLocal09<T> extends InheritableThreadLocal<T>{

    public static ThreadLocal<Stu> threadLocal = new InheritableThreadLocal09<>();
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
        System.out.println("主线程读取本地变量：" + threadLocal.get());

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });
    }


    @Override
    protected T childValue(T parentValue) {
        String s = JSONObject.toJSONString(parentValue);
        return (T)JSONObject.parseObject(s,parentValue.getClass());
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
    }
}
