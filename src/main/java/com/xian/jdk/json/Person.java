package com.xian.jdk.json;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2022/12/20 16:35
 * @Version: 1.0.0
 */
public class Person {

    @JSONField(name = "name")
    private String finalName;

    public String getFinalName() {
        return finalName;
    }

    public void setFinalName(String finalName) {
        this.finalName = finalName;
    }
}
