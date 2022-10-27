package com.xian.jdk.until;

import cn.hutool.extra.pinyin.PinyinUtil;
import org.springframework.util.Assert;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现中文转拼音
 *
 * @author ruanjiayu
 */
public class LanguageConvertUtil {

    /**
     * 获取汉字的拼音首字母
     *
     * @param src 汉字字符串
     * @return
     */
    public static String resolveFirstCharacter(String src) {
        Assert.notNull(src, "参数[src]不能为空");
        // 中文就将首字转成拼音
        String c = ((PinyinUtil.isChinese(src.charAt(0)) ? PinyinUtil.getPinyin(src.charAt(0)) : src)
//                 .substring(0, 1)
        ).toUpperCase(Locale.ROOT);
        return containsLetter(c) ? c : "#";
    }

    /**
     * 是否包含字母
     *
     * @param str
     * @return
     */
    public static boolean containsLetter(String str) {
        String pattern = "[a-z|A-Z]";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(str);
        return matcher.find();
    }


    public static void main(String[] args) {
//        System.out.println(resolveFirstCharacter("阮佳裕"));
//        System.out.println(resolveFirstCharacter("1佳裕"));
//        System.out.println(resolveFirstCharacter("dasf佳裕"));
//        System.out.println(resolveFirstCharacter("🐷⚽️哈哈"));
//        System.out.println(resolveFirstCharacter("7⃣️ℹ️大城市"));
//        System.out.println(resolveFirstCharacter("🌧️大城市"));
//        System.out.println(resolveFirstCharacter("🎺大城市"));

        System.out.println("https://publicduoguan.oss-cn-hangzhou.aliyuncs.com/app/user/icon/10000303/100003031665301177666.jpg"
                .replaceFirst("publicduoguan.oss-cn-hangzhou.aliyuncs.com", "publicduoguan.oss.duoguan.cc"));
    }
}
