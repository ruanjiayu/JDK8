package com.xian.jdk.until;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GenerateAESKey {

    public static void main(String[] args) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(192); // 使用192位密钥长度
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] aesKeyBytes = secretKey.getEncoded();

            // 打印生成的密钥，以便将其用于加密和解密
            System.out.println("AES Key (Base64 Encoded): " + Base64.getEncoder().encodeToString(aesKeyBytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
