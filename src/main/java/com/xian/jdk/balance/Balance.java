package com.xian.jdk.balance;

import java.util.List;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2022/9/14 11:25
 * @Version: 1.0.0
 */
public interface Balance<T> {

    T chooseOne(List<T> list);
}
