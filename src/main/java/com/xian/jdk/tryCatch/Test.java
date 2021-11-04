package com.xian.jdk.tryCatch;


/**
 * @Description: 在10_000_000 次下
 * 字符串
 * 没try_catch,花费时间为580， 582
 * try_catch，花费时间为782，
 * try_catch,抛出异常为926, 1229
 *
 * 数字
 * 在100_000_000次数下
 * 没try_catch,花费时间为 50毫秒
 * 在try_catch,花费时间为50毫秒
 * try_catch,抛出异常后花费时间为62122
 *
 * @Author: Summer
 * @DateTime: 2021/11/4 1:54 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class Test {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < 100_000_000; i++) {
            try {
                a = a + i;
                throw new Exception("出现异常");
            }catch (Exception e){

            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
