package com.xian.jdk.bo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Xian
 * @CreateDate: 2019/8/28  16:05
 * @Version: 0.0.1-SHAPSHOT
 */
public class StudentTest {

    /**
     * 过滤序列内的数据，得到想要的
     */
    @Test
    public void filterTest() {

        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        List<Student> streamStudents = students.stream().filter(s -> "浙江".equals(s.getAddress())).collect(Collectors.toList());
        streamStudents.forEach(System.out::println);
//       foreach有两种方式一种是stream的，一种是 iterable的
        students.stream().filter(student -> "北京".equals(student.getAddress())).forEach(System.out::println);

    }

    /**
     * map就是将对应的元素按照给定的方法进行转换。
     */
    @Test
    public void mapTest() {
        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        List<String> streamStudents = students.stream().map(s -> "地址" + s.getAddress()).collect(Collectors.toList());
        streamStudents.forEach(a -> System.out.println(a));
    }

    /**
     * distinct对字符串数据进行进行去重
     */
    @Test
    public void distinctStringTest() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "1", "4");
        list.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * 可以对对象进行过滤，但是需要对创建的Student对象重写了equals和hashCode()方法，否则去重是无效的。
     */
    @Test
    public void distinctObjectTest() {
        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        Student s5 = new Student(1L, "肖战", 15, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        students.stream().distinct().forEach(System.out::println);
    }

    /**
     * 对简单数据的基础排序
     */
    @Test
    public void sortBaseTest() {
        List<String> list = Arrays.asList("2", "3", "1", "2", "5", "4");
        list.stream().sorted().forEach(System.out::println);
    }

    /**
     * 指定排序规则，先按照学生的id进行降序排序，再按照年龄进行升序排序
     */
    @Test
    public void sortObjectTest() {
        Student s1 = new Student(1L, "肖战", 17, "浙江");
        Student s2 = new Student(1L, "王一博", 16, "湖北");
        Student s3 = new Student(4L, "杨紫", 17, "北京");
        Student s4 = new Student(3L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        // 提示不能进行比较，需要对compare的方法进行重新编写
//        students.stream().sorted().forEach(System.out::println);
        //对对象的ID进行正常的排序
//        students.stream().sorted((stu1, stu2) -> Long.compare(stu1.getId(), stu2.getId())).forEach(System.out::println);
//        students.stream().sorted(Comparator.comparingLong(Student::getId)).forEach(System.out::println);
        students.stream()
                .sorted((stu1, stu2) -> Long.compare(stu2.getId(), stu1.getId()))
                .sorted(Comparator.comparingInt(Student::getAge))
                .forEach(System.out::println);
    }

    /**
     * 限制返回的个数
     */
    @Test
    public void limitTest() {
        List<String> list = Arrays.asList("1", "2", "3", "4");
        list.stream().limit(2L).forEach(System.out::println);
    }


    /**
     * 聚合：将输出->阮佳裕绍兴欢迎你
     */
    @Test
    public void reduceTest() {
        List<String> list = Arrays.asList("绍兴", "欢迎", "你");
        String str = list.stream().reduce("阮佳裕", (a, b) -> a + b);
        System.out.println(str);
//        阮佳裕绍兴欢迎你
    }

    /**
     * 找到最小的值的对象
     */
    @Test
    public void minTest() {
        Student s1 = new Student(1L, "肖战", 14, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 13, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        System.out.println(students.stream().min(Comparator.comparingInt(Student::getAge)).get());
        System.out.println(students.stream().max((stu1, stu2) -> Integer.compare(stu2.getAge(), stu1.getAge())).get());
    }

    /**
     * 找到最大值的对象
     */
    @Test
    public void maxTest() {
        Student s1 = new Student(1L, "肖战", 14, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 13, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        System.out.println(students.stream().min((stu1, stu2) -> Integer.compare(stu2.getAge(), stu1.getAge())).get());
        System.out.println(students.stream().max(Comparator.comparingInt(Student::getAge)).get());
    }

    /**
     * anyMatch 只要有一个正确就是true
     * allMatch 一定要全部正确才是true
     * noneMatch 一直要全部不正确才是true
     */
    @Test
    public void matchTest() {
        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨洋", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        Boolean anyMatch = students.stream().anyMatch(s -> "湖北".equals(s.getAddress()));
        Boolean allMatch = students.stream().allMatch(s -> s.getAge() >= 15);
        Boolean noneMatch = students.stream().noneMatch(s -> "杨洋".equals(s.getName()));
        if (anyMatch) {
            System.out.println("【有湖北人存在】");
        }
        if (allMatch) {
            System.out.println("【年龄都大于15岁】");
        }
        if (noneMatch) {
            System.out.println("【没有叫杨洋的】");
        }
    }


}

