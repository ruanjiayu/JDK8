package com.xian.jdk.balance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: 随机模式轮训
 * @Author: summer
 * @CreateDate: 2022/9/14 11:26
 * @Version: 1.0.0
 */
public class RandomBalance<T> implements Balance<T>{


    private Random random = new Random();

    @Override
    public T chooseOne(List<T> list) {
        int sum = list.size();
        return list.get(random.nextInt(sum));
    }

    /**
     * 测试
     **/
    public static void main(String[] args) {
        List<Service> services = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Service e = new Service();
            e.setIp("address:"+i);
            e.setWeight(1);
            services.add(e);
        }
        RandomBalance<Service> bl = new RandomBalance<>();
        for (int i = 0; i < services.size(); i++) {
            System.out.println(bl.chooseOne(services));
        }
    }


}
