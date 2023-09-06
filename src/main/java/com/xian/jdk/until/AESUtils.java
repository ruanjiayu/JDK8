package com.xian.jdk.until;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @Description: 方便URL传输
 * @Author: summer
 * @CreateDate: 2023/4/17 09:16
 * @Version: 1.0.0
 */
public class AESUtils {

    public static void main(String[] args) throws Exception {
        String key = "mysecretkey12345"; // 16, 24, or 32 bytes
        String plaintext = "3";

        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);

        String decrypted = decrypt(ciphertext, key);
        System.out.println("Decrypted: " + decrypted);
    }

    // 加密
    public static String encrypt(String plaintext, String key) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(encrypted);
    }

    // 解密
    public static String decrypt(String ciphertext, String key) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decoded = Base64.getUrlDecoder().decode(ciphertext.getBytes());
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

}
