package com.xian.jdk.until;

import com.github.promeg.pinyinhelper.Pinyin;
import org.apache.commons.lang3.StringUtils;

/**
 * 实现中文转拼音
 * @author ruanjiayu
 */
public class LanguageConvertUtil {

    /**
     *  获取汉字的拼音首字母
     * @param chinese  汉字字符串
     * @return
     */
    private static String getFirstLetter(String chinese){
        // 1. 生成拼音
        String pinyin = Pinyin.toPinyin(chinese,"");
        return String.valueOf(pinyin.charAt(0)).toUpperCase();
    }


    public static void main(String[] args) {
//        System.out.println(getFirstLetter("🚀佳裕"));

//        String substring = "😃😏😓😠";
        String substring = "😃😏😓😠";
//        System.out.println(substring);
        System.out.println(function(substring, 5));

    }


    public static String function(String value, int lengthShown) {
        String result;
        if (StringUtils.isBlank(value)) {
            return "";
        }
        if (lengthShown <= 0 || value.length() <= lengthShown) {
            return value;
        }

        try {
            result = value.substring(value.offsetByCodePoints(0, 0),
                    value.offsetByCodePoints(0, lengthShown));
        } catch (Exception e) {
            result = value;
        }

        return result;
    }
}
