package com.xian.jdk;

import com.xian.jdk.bo.Student;
import org.junit.Test;

import java.util.Optional;

/**
 * @Description: 测试一下关于optional相关的方法
 * @Author: Summer
 * @DateTime: 2020/4/7 11:38 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class OptionalTest {




    /**
     * orElse 是一定会执行里面的方法
     * orElseGet 只有在之前的条件为null时候，才会执行。如果可以一定要使用这个方法
     */
    @Test
    public void orElseTest() {
        Student student = new Student(1L, "ruanjiayu", 26, "绍兴");
        System.out.println("optional orElse");
        Optional.ofNullable(student).orElse(Student.createStudent()); // 执行方法createStudent()
        System.out.println("optional orElseGet");
        Optional.ofNullable(student).orElseGet(() -> Student.createStudent()); // 不会进行输出createStudent()
    }




}
