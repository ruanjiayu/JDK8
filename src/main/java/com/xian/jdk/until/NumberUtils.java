package com.xian.jdk.until;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/5/8 17:32
 * @Version: 1.0.0
 */
public class NumberUtils {

    /**
     *
     * @return
     */
    public static String int2Hex(int number, int width) {
        return String.format("%0" + width + "x", number);
    }


    public static void main(String[] args) {
        System.out.println(int2Hex(1, 16));
    }
}
