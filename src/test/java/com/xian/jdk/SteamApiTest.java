package com.xian.jdk;

import com.xian.jdk.bo.Student;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Description: 创建Stream的几种方式
 * @Author: Summer
 * @DateTime: 2020/4/6 11:04 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class SteamApiTest {

    /**
     * 通过集合来创建Stream流
     */
    @Test
    public void listTest() {
        List<Student> students = Student.findStudents();

        // 创建一个顺序流
        Stream<Student> stream = students.stream();

        // 创建一个并行流
        Stream<Student> parallelStream = students.parallelStream();
    }


    /**
     * 通过数组来创建Stream流
     */
    @Test
    public void arrayTest() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        IntStream stream = Arrays.stream(arr);
    }

    /**
     * 通过Stream.of方式来创建
     */
    @Test
    public void ofTest() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
    }


    /**
     * 创建无限流的方式
     */
    @Test
    public void test() {
        // 迭代
        Stream.iterate(0, t -> t + 2).limit(100).forEach(System.out::println);


        // 生成
        Stream.generate(() -> Math.random() * 10).limit(100).forEach(System.out::println);
    }
}
