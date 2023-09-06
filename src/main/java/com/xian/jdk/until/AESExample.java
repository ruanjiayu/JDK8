package com.xian.jdk.until;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESExample {
    private static byte[] iv = "0000000000000000".getBytes();

    public static String encrypt128(String content, String key) throws Exception {
        byte[] input = content.getBytes("utf-8");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(DatatypeConverter.parseHexBinary(key), "AES"), new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
        return DatatypeConverter.printHexBinary(encrypted);
    }

    public static String decrypt128(String encrypted, String key) throws Exception {
        Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        dcipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(DatatypeConverter.parseHexBinary(key), "AES"), new IvParameterSpec(iv));
        byte[] clearbyte = dcipher.doFinal(DatatypeConverter
                .parseHexBinary(encrypted));
        return new String(clearbyte);
    }

    public static void main(String[] args) throws Exception {
        String data = "Here is my string 中文测试";
        String key = "d7900701209d3fbac4e214dfeb5f230f";
        String encStr = AESExample.encrypt128(data, key);
        System.out.println(encStr);		//3EB7CF373E108ACA93E85D170C000938E47C8E3C4572A7F3A33C56331BB1CCB5
        System.out.println(AESExample.decrypt128(encStr,  key));
    }
}