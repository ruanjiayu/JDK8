package com.xian.jdk.bo;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @Description: jdk 8 时间的测试
 * @Author: Xian
 * @CreateDate: 2019/9/30  13:13
 * @Version: 0.0.1-SHAPSHOT
 */
public class TimeTest {

    /**
     * Instant：时间戳 不是东八区的司机拿
     * Duration：持续时间，时间差
     * LocalDate：只包含日期，比如：2016-10-20
     * LocalTime：只包含时间，比如：23:12:10
     * LocalDateTime：包含日期和时间，比如：2016-10-20 23:14:21
     * Period：时间段
     * ZoneOffset：时区偏移量，比如：+8:00
     * ZonedDateTime：带时区的时间
     * Clock：时钟，比如获取目前美国纽约的时间
     */

    /**
     * 从默认时区的系统时钟获取当前时间。
     */
    @Test
    public void now() {
        LocalDate todayDate = LocalDate.now();
        // 2019-09-30
        System.out.println("【今天的日期】：" + todayDate);

        //获取当前的时间
        LocalTime nowTime = LocalTime.now(); //结果14:29:40.558
        System.out.println("【LocalTime-nowTime】:" + nowTime);
        //如果不想显示毫秒
        LocalTime nowTime2 = LocalTime.now().withNano(0); //14:43:14
        System.out.println("【LocalTime-nowTime2】:" + nowTime2);
        //指定时间
        LocalTime time = LocalTime.of(14, 10, 21); //14:10:21
        System.out.println("【指定时间】:" + time);
        LocalTime time2 = LocalTime.parse("12:00:01"); // 12:00:01
        System.out.println("【指定时间】:" + time2);

        //当前时间增加2小时
        LocalTime nowTimePlus2Hour = nowTime.plusHours(2); //16:47:23.144
        System.out.println("【当前时间添加两小时】:" + nowTimePlus2Hour.withNano(0));
        //或者
        LocalTime nowTimePlus2Hour2 = nowTime.plus(2, ChronoUnit.HOURS);
        System.out.println("【当前时间添加两小时】:" + nowTimePlus2Hour2);

    }

    /**
     * 获取指定的时间
     */
    @Test
    public void gainAppointedTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("年:"+now.getYear());
        System.out.println("月:"+now.getMonthValue());
        System.out.println("日:"+now.getDayOfMonth());
        System.out.println("时:"+now.getHour());
        System.out.println("分:"+now.getMinute());
        System.out.println("秒:"+now.getSecond());
        System.out.println("该日期是该年的第"+now.getDayOfYear()+"天");
    }


    /**
     * 对时间进行操作
     */
    @Test
    public void operatorTime() {
        LocalDate oneday = LocalDate.now();

        //当前月的第一天的日期 2019-09-01
        LocalDate firstDay = oneday.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDay);

        //当前月的第一天的日期，另外一种写法
        LocalDate firstDay2 = oneday.withDayOfMonth(1);
        System.out.println(firstDay2);

        //当前月的最后一天，不用考虑大月，小月，平年，闰年 2019-09-30
        LocalDate lastDay = oneday.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDay);

        //当前日期＋1天 2019-10-01
        LocalDate tomorrow = oneday.plusDays(1);
        System.out.println(tomorrow);

        //判断是否为闰年
        boolean isLeapYear = tomorrow.isLeapYear();
        System.out.println(isLeapYear);
    }

    /**
     * 验证今天是不是生日
     */
    @Test
    public void validBirthday() {
        LocalDate birthday = LocalDate.of(1995, 11, 20);
        MonthDay birthdayMd = MonthDay.of(birthday.getMonth(), birthday.getDayOfMonth());
        MonthDay today = MonthDay.from(LocalDate.of(2019, 11, 20));
        System.out.println(today.equals(birthdayMd));
    }


    /**
     * 比较两个时间的先后问题
     */
    @Test
    public void compare() {
        LocalDate today = LocalDate.now();
        LocalDate specifyDate = LocalDate.of(2015, 10, 20);
        System.out.println(today.isAfter(specifyDate)); //true
    }

    /**
     * 带时区的时间
     */
    @Test
    public void zoneTime() {
        //查看当前的时区
        ZoneId defaultZone = ZoneId.systemDefault();
        System.out.println(defaultZone); //Asia/Shanghai

        //查看美国纽约当前的时间
        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime shanghaiTime = LocalDateTime.now();
        LocalDateTime americaDateTime = LocalDateTime.now(america);
        System.out.println(shanghaiTime); //2019-09-30T13:46:48.673
        System.out.println(americaDateTime); //2019-09-30T01:46:48.673 ，可以看到美国与北京时间差了12小时

        //带有时区的时间
        ZonedDateTime americaZoneDateTime = ZonedDateTime.now(america);
        System.out.println(americaZoneDateTime); //2019-09-30T01:46:48.683-04:00[America/New_York]
    }

    /**
     * 获得两个时间之间的间隔
     */
    @Test
    public void timeInterval() {
        LocalDate today = LocalDate.now();
        LocalDate specifyDate = LocalDate.of(2015, 8, 2);
        Period period = Period.between(specifyDate, today);

        //4 这里的数字仅仅表示指定的第几号 + 几天就等于现在的几号
        System.out.println(period.getDays());
        //1 这里的数字仅仅表示指定的月份 + 几个月 就等于现在的月份，如果指定的月份大于当前月，那么就需要轮回一下，也就是只能向上加，不能减
        System.out.println(period.getMonths());
        // 输出指定的时间到现在的时间之间，一共间隔了多少议案
        System.out.println(specifyDate.until(today, ChronoUnit.DAYS)); // 1520
    }

    /**
     * 使用DateTimeFormatter取代SimpleDateFormat
     */
    @Test
    public void format() {
//        String specifyDate = "20151011";
//        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
//        LocalDate formatted = LocalDate.parse(specifyDate,formatter);
//        System.out.println(formatted);
//
//        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("YYYY MM dd");
//        System.out.println(formatter2.format(LocalDate.now()));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
//格式化
        String format = now.format(formatter);
        String format2 = now.format(formatter2);
        String format3 = now.format(formatter3);
        System.out.println("格式化1:" + format);
        System.out.println("格式化2:" + format2);
        System.out.println("格式化3:" + format3);
//解析
        LocalDateTime localDateTime = LocalDateTime.parse(format, formatter);
        LocalDate localDate = LocalDate.parse(format2, formatter2);
        LocalTime localTime = LocalTime.parse(format3, formatter3);
        System.out.println("解析1:" + localDateTime);
        System.out.println("解析2:" + localDate);
        System.out.println("解析3:" + localTime);
    }

    /**
     * Date 和 jdk8内时间进行转换
     */
    @Test
    public void transform() {
        //Date与Instant的相互转化
        Instant instant  = Instant.now();
        Date date = Date.from(instant);
        Instant instant2 = date.toInstant();
        System.out.println("【Date -> Instant】:" + instant2);
        //Date转为LocalDateTime
        Date date2 = new Date();
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault());
        System.out.println("【Date -> LocalDateTime】:" + localDateTime2);
        //LocalDateTime转Date
        LocalDateTime localDateTime3 = LocalDateTime.now();
        Instant instant3 = localDateTime3.atZone(ZoneId.systemDefault()).toInstant();
        Date date3 = Date.from(instant);
        System.out.println("【LocalDateTime -> Instant】:" + instant3);
        System.out.println("【LocalDateTime -> Date】:" + date3);

        //LocalDate转Date
        //因为LocalDate不包含时间，所以转Date时，会默认转为当天的起始时间，00:00:00
        LocalDate localDate4 = LocalDate.now();
        Instant instant4 = localDate4.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        System.out.println("【LocalDate -> Instant】:" + instant4);
        Date date4 = Date.from(instant);
        System.out.println("【LocalDate -> Date】:" + date4);
    }
}
