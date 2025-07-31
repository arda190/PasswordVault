package com.example.demo;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES {

    public static SecretKey generateKey()throws Exception{
        KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey=keyGenerator.generateKey();
        return secretKey;
    }

    public static String encrypt(String plainText,String key)throws Exception{
        Cipher cipher=Cipher.getInstance("AES");
        SecretKey secretKey=getKeyFromString(key);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] encrypted=cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedText,String key)throws Exception{
        Cipher cipher=Cipher.getInstance("AES");
        SecretKey secretKey=getKeyFromString(key);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        return new String(cipher.doFinal(decoded));
    }

    public static SecretKey getKeyFromString(String keyStr) {
        // Anahtarı 16 byte’a kes veya uzat
        byte[] keyBytes = new byte[16]; // 128 bit AES anahtarı
        byte[] parameterKeyBytes = keyStr.getBytes(StandardCharsets.UTF_8);
        int length = Math.min(parameterKeyBytes.length, keyBytes.length);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, length);

        return new SecretKeySpec(keyBytes, "AES");
    }





}
