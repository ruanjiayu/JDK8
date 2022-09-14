package com.xian.jdk.balance;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2022/9/14 11:28
 * @Version: 1.0.0
 */
public class Service {

    private String ip;

    private Integer weight;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Service{" +
                "ip='" + ip + '\'' +
                ", weight=" + weight +
                '}';
    }
}
