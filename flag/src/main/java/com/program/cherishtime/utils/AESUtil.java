package com.program.cherishtime.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 用于加密SharedPreferences，未使用
 */
//@SuppressWarnings("unused")
public class AESUtil {

    private final static String HEX = "0123456789ABCDEF";
    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    //AES 加密
    private static final String AES = "AES";
    // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
    private static final String SHA1PRNG = "SHA1PRNG";

    /**
     * 对秘钥进行处理
     * @param seed 秘钥
     * @return 处理后的密钥
     * @throws Exception 抛出异常
     */
    @SuppressLint("DeletedProvider")
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kGen = KeyGenerator.getInstance(AES);
        //for android
        SecureRandom sr;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        // Android  6.0 以上
        if (sdkVersion > 23) {
            sr = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
        } else if (sdkVersion >= 17) {
            sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
        } else {
            sr = SecureRandom.getInstance(SHA1PRNG);
        }
        sr.setSeed(seed);
        kGen.init(128, sr);
        SecretKey secretKey = kGen.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 加密
     * @param key 密钥
     * @param cleartext 明文
     * @return 密文
     */
    public static String encrypt(String key, String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(key, cleartext.getBytes());
            return new String(Base64.encode(result, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(String key, byte[] clear) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec secretKey = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(clear);
    }

    /**
     * 解密
     * @param key 密钥
     * @param encrypted 密文
     * @return 明文
     */
    public static String decrypt(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = Base64.decode(encrypted, Base64.DEFAULT);
            byte[] result = decrypt(key, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(String key, byte[] encrypted) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec secretKey = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(encrypted);
    }

    public static class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }

}