package com.xian.jdk.until;

import com.google.common.base.Splitter;

import java.net.URI;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/4/17 10:37
 * @Version: 1.0.0
 */
public class UrlUtils {

    public static final String TOKEN = "token";

    public static void main(String[] args) throws Exception {
        URI uri = new URI("http://www.fununcle.top?token=1&userId=2");
        System.out.println(decrypt(uri));
        System.out.println(Objects.equals(null, "hah"));
    }

    // 解密
    public static String decrypt(URI uri) throws Exception {
        String query = URLDecoder.decode(uri.getQuery(), "UTF-8");
        Map<String, String> queryMap = Splitter.on("&").withKeyValueSeparator("=").split(query);
        return queryMap.get(TOKEN);
    }
}
