package com.xian.jdk.balance;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 加权模式。(简单版本)
 * 存在问题，1，2，2，2，3，3，3，3，3。是这样调用服务，导致调用的次数不是平滑分布。
 * @Author: summer
 * @CreateDate: 2022/9/14 14:09
 * @Version: 1.0.0
 */
public class WeightBalance implements Balance<Service>{

    private volatile static int index;

    @Override
    public synchronized Service chooseOne(List<Service> list) {
        int sum = list.stream().mapToInt(Service::getWeight).sum();
        int temp = 0;
        int cur = (index++) % sum;
        for (Service service : list) {
            temp = temp + service.getWeight();
            if(cur < temp) {
                return service;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<Service> services = new ArrayList<>();

        Service server1 = new Service();
        server1.setIp("address1");
        server1.setWeight(1);

        Service server2 = new Service();
        server2.setIp("address2");
        server2.setWeight(3);

        Service server3 = new Service();
        server3.setIp("address3");
        server3.setWeight(5);

        services.add(server1);
        services.add(server2);
        services.add(server3);

        WeightBalance loadBalance = new WeightBalance();

        for (int i = 0; i < 20; i++) {
            System.out.println("第"+i+"次请求服务ip为："+loadBalance.chooseOne(services).getIp());
        }
    }

}
