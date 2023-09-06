package com.xian.jdk.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @Description: jdk 8 时间的测试
 * @Author: Xian
 * @CreateDate: 2019/9/30  13:13
 * @Version: 0.0.1-SHAPSHOT
 */
public class TimeTest {

    /**
     * Instant：时间戳 不是东八区的时间
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
        System.out.println("今天是周" + now.getDayOfWeek().getValue());
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
        LocalDate specifyDate = LocalDate.of(2023, 8, 5);
        System.out.println(ChronoUnit.DAYS.between(specifyDate.atStartOfDay(), LocalDate.now().atStartOfDay()));
        Period period = Period.between(specifyDate, today);

        //4 这里的数字仅仅表示指定的第几号 + 几天就等于现在的几号
        System.out.println(period.getDays());
        //1 这里的数字仅仅表示指定的月份 + 几个月 就等于现在的月份，如果指定的月份大于当前月，那么就需要轮回一下，也就是只能向上加，不能减
        System.out.println(period.getMonths());
        // 输出指定的时间到现在的时间之间，一共间隔了多少议案
        System.out.println(specifyDate.until(today, ChronoUnit.DAYS)); // 1520

        long between = ChronoUnit.MILLIS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        System.out.println(between);
        System.out.println(String.format("%.3f", between / 1000.0));
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


    /**
     * 构建时间
     */
    @Test
    public void buildTest() {
        LocalDate localDate = LocalDate.now();
        LocalTime time = LocalTime.of(8, 0, 0);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
        System.out.println(localDateTime);
        System.out.println(LocalDateTime.parse("2022-11-29T11:00:00"));
        System.out.println(Boolean.TRUE.equals(null));
        System.out.println(Boolean.TRUE.equals("true"));
        System.out.println((HashMap<String, Object>) null);
    }

    /**
     * 日期国际化展示 Mar 23, 2023
     */
    @Test
    public void msTest() {
        // 指定日期为美国
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.US);
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Test
    public void formatStyle() {
        System.out.println(getVideoFormat(30));
        System.out.println(getVideoFormat(60));
        System.out.println(getVideoFormat(90));
        System.out.println(getVideoFormat(5000));
    }

    /**
     * 将秒数转化为时分秒格式
     *
     * @param time
     * @return
     */
    public static String getVideoFormat(long time) {

        int temp = (int) time;
        int mm = temp / 60;
        int ss = (temp % 3600) % 60;
        return (mm < 10 ? ("0" + mm) : mm) + ":" +
                (ss < 10 ? ("0" + ss) : ss);
    }

    @Test
    public void stringTest() {
        String str = "[{\"key\":\"218.0\",\"doc_count\":80},{\"key\":\"227.0\",\"doc_count\":49},{\"key\":\"300.0\",\"doc_count\":32},{\"key\":\"255.0\",\"doc_count\":28},{\"key\":\"294.0\",\"doc_count\":25},{\"key\":\"221.0\",\"doc_count\":16},{\"key\":\"233.0\",\"doc_count\":14},{\"key\":\"100004804\",\"doc_count\":13},{\"key\":\"100005073\",\"doc_count\":10},{\"key\":\"328.0\",\"doc_count\":10},{\"key\":\"100004865\",\"doc_count\":9},{\"key\":\"100004901\",\"doc_count\":9},{\"key\":\"254.0\",\"doc_count\":9},{\"key\":\"100003169\",\"doc_count\":8},{\"key\":\"100004805\",\"doc_count\":7},{\"key\":\"222.0\",\"doc_count\":7},{\"key\":\"346.0\",\"doc_count\":7},{\"key\":\"100003437\",\"doc_count\":6},{\"key\":\"100004821\",\"doc_count\":6},{\"key\":\"224.0\",\"doc_count\":6},{\"key\":\"100003412\",\"doc_count\":5},{\"key\":\"100003895\",\"doc_count\":5},{\"key\":\"100004825\",\"doc_count\":5},{\"key\":\"100004889\",\"doc_count\":5},{\"key\":\"228.0\",\"doc_count\":5},{\"key\":\"319.0\",\"doc_count\":5},{\"key\":\"100003394\",\"doc_count\":4},{\"key\":\"100003443\",\"doc_count\":4},{\"key\":\"100003490\",\"doc_count\":4},{\"key\":\"100003861\",\"doc_count\":4},{\"key\":\"100003899\",\"doc_count\":4},{\"key\":\"100004479\",\"doc_count\":4},{\"key\":\"100005086\",\"doc_count\":4},{\"key\":\"1905.0\",\"doc_count\":4},{\"key\":\"343.0\",\"doc_count\":4},{\"key\":\"458.0\",\"doc_count\":4},{\"key\":\"581.0\",\"doc_count\":4},{\"key\":\"1\",\"doc_count\":3},{\"key\":\"100003409\",\"doc_count\":3},{\"key\":\"100003411\",\"doc_count\":3},{\"key\":\"100003422\",\"doc_count\":3},{\"key\":\"100003440\",\"doc_count\":3},{\"key\":\"100003850\",\"doc_count\":3},{\"key\":\"100003858\",\"doc_count\":3},{\"key\":\"100003878\",\"doc_count\":3},{\"key\":\"100004522\",\"doc_count\":3},{\"key\":\"100004810\",\"doc_count\":3},{\"key\":\"100004850\",\"doc_count\":3},{\"key\":\"100004855\",\"doc_count\":3},{\"key\":\"100004902\",\"doc_count\":3},{\"key\":\"100004917\",\"doc_count\":3},{\"key\":\"100004926\",\"doc_count\":3},{\"key\":\"100004929\",\"doc_count\":3},{\"key\":\"100004933\",\"doc_count\":3},{\"key\":\"100004949\",\"doc_count\":3},{\"key\":\"100004958\",\"doc_count\":3},{\"key\":\"100005062\",\"doc_count\":3},{\"key\":\"1895.0\",\"doc_count\":3},{\"key\":\"1909.0\",\"doc_count\":3},{\"key\":\"217.0\",\"doc_count\":3},{\"key\":\"279.0\",\"doc_count\":3},{\"key\":\"316.0\",\"doc_count\":3},{\"key\":\"334.0\",\"doc_count\":3},{\"key\":\"773.0\",\"doc_count\":3},{\"key\":\"100000299\",\"doc_count\":2},{\"key\":\"100003396\",\"doc_count\":2},{\"key\":\"100003400\",\"doc_count\":2},{\"key\":\"100003407\",\"doc_count\":2},{\"key\":\"100003425\",\"doc_count\":2},{\"key\":\"100003438\",\"doc_count\":2},{\"key\":\"100003441\",\"doc_count\":2},{\"key\":\"100003442\",\"doc_count\":2},{\"key\":\"100003466\",\"doc_count\":2},{\"key\":\"100003561\",\"doc_count\":2},{\"key\":\"100003855\",\"doc_count\":2},{\"key\":\"100003866\",\"doc_count\":2},{\"key\":\"100003870\",\"doc_count\":2},{\"key\":\"100003871\",\"doc_count\":2},{\"key\":\"100003872\",\"doc_count\":2},{\"key\":\"100003876\",\"doc_count\":2},{\"key\":\"100003885\",\"doc_count\":2},{\"key\":\"100003891\",\"doc_count\":2},{\"key\":\"100004004\",\"doc_count\":2},{\"key\":\"100004484\",\"doc_count\":2},{\"key\":\"100004557\",\"doc_count\":2},{\"key\":\"100004730\",\"doc_count\":2},{\"key\":\"100004751\",\"doc_count\":2},{\"key\":\"100004765\",\"doc_count\":2},{\"key\":\"100004766\",\"doc_count\":2},{\"key\":\"100004786\",\"doc_count\":2},{\"key\":\"100004792\",\"doc_count\":2},{\"key\":\"100004798\",\"doc_count\":2},{\"key\":\"100004814\",\"doc_count\":2},{\"key\":\"100004817\",\"doc_count\":2},{\"key\":\"100004857\",\"doc_count\":2},{\"key\":\"100004876\",\"doc_count\":2},{\"key\":\"100004892\",\"doc_count\":2},{\"key\":\"100004909\",\"doc_count\":2},{\"key\":\"100004927\",\"doc_count\":2},{\"key\":\"100004928\",\"doc_count\":2},{\"key\":\"100004937\",\"doc_count\":2},{\"key\":\"100004968\",\"doc_count\":2},{\"key\":\"100004969\",\"doc_count\":2},{\"key\":\"100004977\",\"doc_count\":2},{\"key\":\"100005000\",\"doc_count\":2},{\"key\":\"100005055\",\"doc_count\":2},{\"key\":\"100005058\",\"doc_count\":2},{\"key\":\"100005061\",\"doc_count\":2},{\"key\":\"100005063\",\"doc_count\":2},{\"key\":\"100005065\",\"doc_count\":2},{\"key\":\"100005079\",\"doc_count\":2},{\"key\":\"100005092\",\"doc_count\":2},{\"key\":\"100005132\",\"doc_count\":2},{\"key\":\"1137.0\",\"doc_count\":2},{\"key\":\"257.0\",\"doc_count\":2},{\"key\":\"646.0\",\"doc_count\":2},{\"key\":\"937.0\",\"doc_count\":2},{\"key\":\"100000002\",\"doc_count\":1},{\"key\":\"100000006\",\"doc_count\":1},{\"key\":\"100000037\",\"doc_count\":1},{\"key\":\"100001311\",\"doc_count\":1},{\"key\":\"100002945\",\"doc_count\":1},{\"key\":\"100003395\",\"doc_count\":1},{\"key\":\"100003399\",\"doc_count\":1},{\"key\":\"100003414\",\"doc_count\":1},{\"key\":\"100003415\",\"doc_count\":1},{\"key\":\"100003420\",\"doc_count\":1},{\"key\":\"100003430\",\"doc_count\":1},{\"key\":\"100003434\",\"doc_count\":1},{\"key\":\"100003436\",\"doc_count\":1},{\"key\":\"100003449\",\"doc_count\":1},{\"key\":\"100003453\",\"doc_count\":1},{\"key\":\"100003463\",\"doc_count\":1},{\"key\":\"100003480\",\"doc_count\":1},{\"key\":\"100003501\",\"doc_count\":1},{\"key\":\"100003518\",\"doc_count\":1},{\"key\":\"100003531\",\"doc_count\":1},{\"key\":\"100003549\",\"doc_count\":1},{\"key\":\"100003566\",\"doc_count\":1},{\"key\":\"100003633\",\"doc_count\":1},{\"key\":\"100003706\",\"doc_count\":1},{\"key\":\"100003831\",\"doc_count\":1},{\"key\":\"100003836\",\"doc_count\":1},{\"key\":\"100003847\",\"doc_count\":1},{\"key\":\"100003853\",\"doc_count\":1},{\"key\":\"100003867\",\"doc_count\":1},{\"key\":\"100003875\",\"doc_count\":1},{\"key\":\"100003879\",\"doc_count\":1},{\"key\":\"100004040\",\"doc_count\":1},{\"key\":\"100004122\",\"doc_count\":1},{\"key\":\"100004132\",\"doc_count\":1},{\"key\":\"100004409\",\"doc_count\":1},{\"key\":\"100004482\",\"doc_count\":1},{\"key\":\"100004485\",\"doc_count\":1},{\"key\":\"100004486\",\"doc_count\":1},{\"key\":\"100004556\",\"doc_count\":1},{\"key\":\"100004565\",\"doc_count\":1},{\"key\":\"100004615\",\"doc_count\":1},{\"key\":\"100004634\",\"doc_count\":1},{\"key\":\"100004721\",\"doc_count\":1},{\"key\":\"100004726\",\"doc_count\":1},{\"key\":\"100004732\",\"doc_count\":1},{\"key\":\"100004737\",\"doc_count\":1},{\"key\":\"100004740\",\"doc_count\":1},{\"key\":\"100004776\",\"doc_count\":1},{\"key\":\"100004790\",\"doc_count\":1},{\"key\":\"100004793\",\"doc_count\":1},{\"key\":\"100004795\",\"doc_count\":1},{\"key\":\"100004799\",\"doc_count\":1},{\"key\":\"100004802\",\"doc_count\":1},{\"key\":\"100004806\",\"doc_count\":1},{\"key\":\"100004811\",\"doc_count\":1},{\"key\":\"100004819\",\"doc_count\":1},{\"key\":\"100004820\",\"doc_count\":1},{\"key\":\"100004831\",\"doc_count\":1},{\"key\":\"100004836\",\"doc_count\":1},{\"key\":\"100004840\",\"doc_count\":1},{\"key\":\"100004844\",\"doc_count\":1},{\"key\":\"100004845\",\"doc_count\":1},{\"key\":\"100004846\",\"doc_count\":1},{\"key\":\"100004847\",\"doc_count\":1},{\"key\":\"100004848\",\"doc_count\":1},{\"key\":\"100004849\",\"doc_count\":1},{\"key\":\"100004854\",\"doc_count\":1},{\"key\":\"100004861\",\"doc_count\":1},{\"key\":\"100004862\",\"doc_count\":1},{\"key\":\"100004869\",\"doc_count\":1},{\"key\":\"100004872\",\"doc_count\":1},{\"key\":\"100004877\",\"doc_count\":1},{\"key\":\"100004881\",\"doc_count\":1},{\"key\":\"100004883\",\"doc_count\":1},{\"key\":\"100004897\",\"doc_count\":1},{\"key\":\"100004899\",\"doc_count\":1},{\"key\":\"100004903\",\"doc_count\":1},{\"key\":\"100004904\",\"doc_count\":1},{\"key\":\"100004907\",\"doc_count\":1},{\"key\":\"100004914\",\"doc_count\":1},{\"key\":\"100004919\",\"doc_count\":1},{\"key\":\"100004925\",\"doc_count\":1},{\"key\":\"100004930\",\"doc_count\":1},{\"key\":\"100004938\",\"doc_count\":1},{\"key\":\"100004939\",\"doc_count\":1},{\"key\":\"100004940\",\"doc_count\":1},{\"key\":\"100004944\",\"doc_count\":1},{\"key\":\"100004950\",\"doc_count\":1},{\"key\":\"100004956\",\"doc_count\":1},{\"key\":\"100004960\",\"doc_count\":1},{\"key\":\"100004962\",\"doc_count\":1},{\"key\":\"100004975\",\"doc_count\":1},{\"key\":\"100004981\",\"doc_count\":1},{\"key\":\"100004987\",\"doc_count\":1},{\"key\":\"100004999\",\"doc_count\":1},{\"key\":\"100005006\",\"doc_count\":1},{\"key\":\"100005009\",\"doc_count\":1},{\"key\":\"100005010\",\"doc_count\":1},{\"key\":\"100005012\",\"doc_count\":1},{\"key\":\"100005013\",\"doc_count\":1},{\"key\":\"100005015\",\"doc_count\":1},{\"key\":\"100005029\",\"doc_count\":1},{\"key\":\"100005044\",\"doc_count\":1},{\"key\":\"100005046\",\"doc_count\":1},{\"key\":\"100005048\",\"doc_count\":1},{\"key\":\"100005050\",\"doc_count\":1},{\"key\":\"100005053\",\"doc_count\":1},{\"key\":\"100005059\",\"doc_count\":1},{\"key\":\"100005069\",\"doc_count\":1},{\"key\":\"100005085\",\"doc_count\":1},{\"key\":\"100005090\",\"doc_count\":1},{\"key\":\"100005091\",\"doc_count\":1},{\"key\":\"100005096\",\"doc_count\":1},{\"key\":\"100005110\",\"doc_count\":1},{\"key\":\"100005114\",\"doc_count\":1},{\"key\":\"100005127\",\"doc_count\":1},{\"key\":\"1006.0\",\"doc_count\":1},{\"key\":\"1037.0\",\"doc_count\":1},{\"key\":\"1107.0\",\"doc_count\":1},{\"key\":\"1133.0\",\"doc_count\":1},{\"key\":\"1378\",\"doc_count\":1},{\"key\":\"1417.0\",\"doc_count\":1},{\"key\":\"1876.0\",\"doc_count\":1},{\"key\":\"1877\",\"doc_count\":1},{\"key\":\"1879.0\",\"doc_count\":1},{\"key\":\"1885\",\"doc_count\":1},{\"key\":\"1886.0\",\"doc_count\":1},{\"key\":\"1887\",\"doc_count\":1},{\"key\":\"1889\",\"doc_count\":1},{\"key\":\"1893\",\"doc_count\":1},{\"key\":\"1894\",\"doc_count\":1},{\"key\":\"1896.0\",\"doc_count\":1},{\"key\":\"1898\",\"doc_count\":1},{\"key\":\"1904.0\",\"doc_count\":1},{\"key\":\"1906\",\"doc_count\":1},{\"key\":\"1907\",\"doc_count\":1},{\"key\":\"1908.0\",\"doc_count\":1},{\"key\":\"1911.0\",\"doc_count\":1},{\"key\":\"236.0\",\"doc_count\":1},{\"key\":\"244.0\",\"doc_count\":1},{\"key\":\"252.0\",\"doc_count\":1},{\"key\":\"265.0\",\"doc_count\":1},{\"key\":\"282.0\",\"doc_count\":1},{\"key\":\"285.0\",\"doc_count\":1},{\"key\":\"305.0\",\"doc_count\":1},{\"key\":\"310.0\",\"doc_count\":1},{\"key\":\"314.0\",\"doc_count\":1},{\"key\":\"318.0\",\"doc_count\":1},{\"key\":\"332.0\",\"doc_count\":1},{\"key\":\"356.0\",\"doc_count\":1},{\"key\":\"358.0\",\"doc_count\":1},{\"key\":\"382.0\",\"doc_count\":1},{\"key\":\"386.0\",\"doc_count\":1},{\"key\":\"424.0\",\"doc_count\":1},{\"key\":\"429.0\",\"doc_count\":1},{\"key\":\"430.0\",\"doc_count\":1},{\"key\":\"449.0\",\"doc_count\":1},{\"key\":\"475.0\",\"doc_count\":1},{\"key\":\"519.0\",\"doc_count\":1},{\"key\":\"548.0\",\"doc_count\":1},{\"key\":\"549.0\",\"doc_count\":1},{\"key\":\"601.0\",\"doc_count\":1},{\"key\":\"632.0\",\"doc_count\":1},{\"key\":\"872.0\",\"doc_count\":1}]";
        JSONArray jsonArray = JSONArray.parseArray(str);

        String haha = "";
        List<Integer> list = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject x = (JSONObject) JSON.toJSON(o);
            list.add(x.getIntValue("key"));
        }
        System.out.println(list);
    }


    @Test
    public void stringTest1() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    @Test
    public void stringTest2() {
        Object obj = 3.14;
        System.out.println((int) Double.parseDouble(obj.toString()));
    }


    @Test
    public void stringTest3() {
        String password = "123456"; // 待加密的字符串
        String encryptedPassword = getMD5(password); // 获得MD5加密后的结果
        System.out.println(encryptedPassword);
    }

        public static String getMD5(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5"); // 使用MD5算法
                md.update(password.getBytes());
                byte[] bytes = md.digest(); // 获得加密后的byte数组
                StringBuilder sb = new StringBuilder();
                for (byte b: bytes) {
                    sb.append(String.format("%02x", b & 0xff)); // 将byte数组转化为16进制的字符串
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

    @Test
    public void stringTest4() {
        String password = "123456";

        // 加密
        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(encodedPassword);

        // 使用正确密码验证密码是否正确
        boolean flag = BCrypt.checkpw(password, encodedPassword);
        System.out.println(flag);

        // 使用错误密码验证密码是否正确
        flag = BCrypt.checkpw(password, "$2a$10$QHciH8/Dc4FnDGxvAo/dF..IGlMOeKLnXa3gF4PPcHzWmw2a0BtTq");
        System.out.println(flag);

        System.out.println(LocalDate.now().isBefore(LocalDate.now()));
    }

}
