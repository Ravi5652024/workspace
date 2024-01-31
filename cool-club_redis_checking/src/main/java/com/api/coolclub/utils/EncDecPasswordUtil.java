package com.api.coolclub.utils;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

/*
 * @Author Rohan_Sharma
*/

@Component
public class EncDecPasswordUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY = "RohanSharma@#$ygjkscmckj!@#$%roahn@$$#";

    private static SecretKeySpec generateSecretKeyDB() throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = SECRET_KEY.getBytes("UTF-8");
        key = sha.digest(key);
        return new SecretKeySpec(key, ALGORITHM);
    }
    
    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKeyDB());
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, generateSecretKeyDB());
        byte[] cipherText = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedText = cipher.doFinal(cipherText);
        return new String(decryptedText);
    }
}
