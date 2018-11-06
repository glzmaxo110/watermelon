package com.xx.watermelon.common.util;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @description: 基于PKCS7Encoder算法辅助类
 * @author:
 * @createTime: 2018-03-29 上午10:28
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/
public class PKCS7Encoder {

    private PKCS7Encoder() {}

    private  static Charset CHARSET = Charset.defaultCharset();
    private  static final int BLOCK_SIZE = 32;

    /**
     * 获得对明文进行补位填充的字节.
     *
     * @param count 需要进行填充补位操作的明文字节个数
     * @return 补齐用的字节数组
     */
    public static byte[] encode(int count) {

        // 计算需要填充的位数
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        if (amountToPad == 0) {

            amountToPad = BLOCK_SIZE;

        }
        // 获得补位所用的字符
        char padChr = chr(amountToPad);
        StringBuilder tmp = new StringBuilder(16);
        int index = 0;
        while (index < amountToPad) {

            tmp.append(padChr);

            index++;
        }
        return  tmp.toString().getBytes(CHARSET);

    }

    /**
     * 删除解密后明文的补位字符
     *
     * @param decrypted 解密后的明文
     * @return 删除补位字符后的明文
     */
    public static byte[] decode(byte[] decrypted) {

        int pad = (int) decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);

    }

    /**
     * 将数字转化成ASCII码对应的字符，用于对明文进行补码
     *
     * @param a 需要转化的数字
     * @return 转化得到的字符
     */
    public   static char chr(int a) {
        byte target = (byte) (a & 0xFF);
        return (char) target;
    }

}
