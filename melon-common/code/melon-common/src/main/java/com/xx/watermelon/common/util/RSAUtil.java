package com.xx.watermelon.common.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @description: RSA加密辅助类
 * @author:
 * @createTime: 2018-03-29 上午10:17
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/
public class RSAUtil {

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String KEY_ALGORITHM = "RSA";

    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static final int MAX_DECRYPT_BLOCK = 128;

    private RSAUtil() {}

    /**
     * RSA签名
     *
     * @param content       待签名数据
     * @param privateKey    私钥
     * @param input_charset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String input_charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            byte[] data = content.getBytes(input_charset);
            signature.initSign(priKey);
            signature.update(data);

            byte[] signed = signature.sign();
            return  new String(Base64.getEncoder().encode(signed), Charset.defaultCharset()) ;
            //return new String(UrlBase64.encode(signed), "UTF-8");
        } catch (Exception e) {
            e.getMessage();
        }

        return null;
    }


    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        // keyBytes = Base64.decode(key);
        byte[] keyBytes  = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return  keyFactory.generatePrivate(keySpec);
    }

    /**
     * 加密私钥
     * @param data
     * @param privateKey  私钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        PrivateKey prikey = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, prikey);

        return dealData(cipher, data, MAX_ENCRYPT_BLOCK);
    }

    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {
        return Base64.getEncoder().encodeToString(encryptByPrivateKey(data.getBytes(Charset.defaultCharset()), privateKey));
    }

    /**
     * 私钥解密
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);

        return dealData(cipher, data, MAX_DECRYPT_BLOCK);
    }

    public static String decryptByPrivateKey(String data, String privateKey) throws Exception {
        byte[] dataBytes = Base64.getDecoder().decode(data);
        return new String(decryptByPrivateKey(dataBytes, privateKey), Charset.defaultCharset());
    }

    private static byte[] dealData(Cipher cipher, byte[] data, int block) throws Exception {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > block) {
                cache = cipher.doFinal(data, offSet, block);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * block;
        }
        byte[] newData = out.toByteArray();
        out.close();

        return newData;
    }
}
