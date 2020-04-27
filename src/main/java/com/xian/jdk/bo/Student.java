package com.xian.jdk.bo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: Xian
 * @CreateDate: 2019/8/28  16:02
 * @Version: 0.0.1-SHAPSHOT
 */
public class Student {
    private Long id;

    private String name;

    private int age;

    private String address;

    public Student() {
    }

    private static Student s1 = new Student(1L, "肖战", 15, "浙江");
    private static Student s2 = new Student(2L, "王一博", 15, "湖北");
    private static Student s3 = new Student(3L, "杨紫", 17, "北京");
    private static Student s4 = new Student(4L, "李现", 17, "浙江");

    /**
     * 来获取student的集合数据
     * @return
     */
    public static List<Student> findStudents() {
        return Arrays.asList(s1, s2, s3, s4);
    }


    public static Student createStudent() {
        System.out.println("创建一个学生");
        Student s5 = new Student(5L, "阮佳裕", 26, "浙江");
        return s5;
    }

    public Student(Long id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    /**
     * 为了实现对象级别的判断是否相同
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(id, student.id) &&
                Objects.equals(name, student.name) &&
                Objects.equals(address, student.address);
    }

    /**
     * 为了实现对象级别的判断是否相同
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, address);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
