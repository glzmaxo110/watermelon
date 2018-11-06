package com.xx.watermelon.common.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * @description: 基于Prpcrypt加解密算法辅助类
 * @author:
 * @createTime: 2018-03-29 上午10:22
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/
public class PrpcryptUtil {

    private static boolean initialized = false;

    private PrpcryptUtil() {}

    /**
     * 加密
     *
     * @param input
     * @param key
     * @return
     */
    public static byte[] encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return crypted;
    }

    /**
     * 解密
     *
     * @param input
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] input, byte[] key, byte[] iv) throws Exception {
        initialize();
        byte[] output;
        try {
            SecretKeySpec skey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey, generateIV(iv));
            output = cipher.doFinal(input);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw e;
        }
        return output;
    }

    private static void initialize() {
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }


    //生成iv
    private static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
}
