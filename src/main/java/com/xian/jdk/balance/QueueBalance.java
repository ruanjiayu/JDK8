package com.xian.jdk.balance;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 顺序轮询模式
 * @Author: summer
 * @CreateDate: 2022/9/14 11:29
 * @Version: 1.0.0
 */
public class QueueBalance<T> implements Balance<T> {

    private volatile int index = 0;

    @Override
    public synchronized T chooseOne(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        int sum = list.size();
        int temp = index % sum;
        T t = list.get(temp);
        index++;
        return t;
    }

    /**
     * 测试
     **/
    public static void main(String[] args) {
        List<Service> services = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Service e = new Service();
            e.setIp("address:" + i);
            e.setWeight(1);
            services.add(e);
        }
        QueueBalance<Service> bl = new QueueBalance<>();
        for (int i = 0; i < services.size(); i++) {
            System.out.println(bl.chooseOne(services));
        }
    }
}
