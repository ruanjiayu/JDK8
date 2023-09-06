package com.xian.jdk.msg;

import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/8/8 11:37
 * @Version: 1.0.0
 */
public class MyClass {

    private int age;

    private String name;

    private List<String> address;

    private HashMap<String, Object> input;

    public MyClass() {
    }

    public MyClass(int age, String name, List<String> address) {
        this.age = age;
        this.name = name;
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public HashMap<String, Object> getInput() {
        return input;
    }

    public void setInput(HashMap<String, Object> input) {
        this.input = input;
    }
}
