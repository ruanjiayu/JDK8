package com.xian.jdk.balance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 权重模式均匀轮询
 * @Author: summer
 * @CreateDate: 2022/9/14 14:16
 * @Version: 1.0.0
 */
public class SmoothWeightBalance implements Balance<Service>{

    /**
     * 存储当前weight
     */
    private Map<String, Integer> map = new HashMap<>();

    /**
     *  加权平滑轮询
     *  	实现原理：将请求为key，权重值初始为value，每一轮取最大的weight，然后将最大的weight-总数，再将每个递增自身weight。
     *  	解析： 每一轮所有server的当前weight递增总数等于最大weight的server减少的总数，从而保证持续取值时减少不会将权重越减
     *  越少。而权重值越大，那么增长时抢占最大权重值的机会越大，这个几率值和初始weight成正比。就好比草长的越快，那么被割的机会越大。
     */
    @Override
    public Service chooseOne(List<Service> services) {
        // 给予每个service的在map中的权重值为自身初始weight
        services.forEach(service ->
                map.computeIfAbsent(service.toString(), key -> service.getWeight())
        );
        int sum = services.stream().mapToInt(Service::getWeight).sum(); // 权重总数

        // 找当前weight值最大的server
        Service maxService = null;
        for (Service service : services) {
            Integer currentWeight = map.get(service.toString());
            if (maxService == null || currentWeight > map.get(maxService.toString())) {
                maxService = service; // 找当前weight最大的server
            }
        }

        // 将最大的 - total总数 (备注：默认weight获取：server.getWeight , 当前weight获取 : map.get(server.toString))
        map.put(maxService.toString(), map.get(maxService.toString()) - sum);

        // 递增所有server的weight值 (总递增数等于total)
        for (Service service : services) {
            Integer oldweight = map.get(service.toString());
            map.put(service.toString(), oldweight + service.getWeight());
        }

        return maxService;
    }


    /**
     * 测试
     */
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

        SmoothWeightBalance loadBalance = new SmoothWeightBalance();

        for (int i = 0; i < 20; i++) {
            System.out.println("第" + i + "次请求服务ip为：" + loadBalance.chooseOne(services).getIp());
        }
    }
}
