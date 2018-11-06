package com.xx.watermelon.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description: md5加解密辅助类
 * @author:
 * @createTime: 2018-03-29 上午11:08
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/
public class MD5Util {

    private MD5Util() {
    }

    private static MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return getMD5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String input_charset) {
        return getMD5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param sign          签名结果
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + key;
        String mysign = getMD5Hex(getContentBytes(text, input_charset));
        return mysign.equals(sign);
    }


    /**
     * @param content
     * @param charset
     * @return byte[]
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (Exception e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 用于获取md5hex值
     *
     * @param bytes
     * @return
     */
    public static String getMD5Hex(byte[] bytes) {
        byte[] bs = md5.digest(bytes);
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    /**
     * MD5加密
     *
     * @param src 源
     * @return string
     */
    public static String getMD5WithBase64Encode(String src) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(src.getBytes());
            byte[] digestSrc = digest.digest();
            //return  Base64.encode(digestSrc);
            return Base64.getEncoder().encodeToString(digestSrc);
            //Encoder encoder = Base64.getEncoder();
            //return encoder.encodeToString( digestSrc );
        } catch (Exception ex) {
            ex.printStackTrace();
            return src;
        }
    }


    /**
     * 创建加盐md5加密方式
     *
     * @param text
     * @param salt
     * @return
     */
    public static String getSaltMD5(String text, String salt) {
        md5.update(salt.getBytes());
        byte[] bytes = md5.digest(text.getBytes());
        return IntStream.range(0, bytes.length).mapToObj(i -> Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                .substring(1)).collect(Collectors.joining());
    }


    /**
     * 生成盐值
     *
     * @return string
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Arrays.toString(salt);
    }
}
