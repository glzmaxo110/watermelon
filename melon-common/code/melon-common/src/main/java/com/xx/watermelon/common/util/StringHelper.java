package com.xx.watermelon.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description: 字符串进行Unicode编码与解码
 * @author:
 * @createTime: 2018-03-28 下午5:27
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/
public class StringHelper {

    private StringHelper() {
    }

    /**
     * 将string编码成Unicode编码字符
     *
     * @param string 字符串
     * @return Unicode字符串
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        IntStream.range(0, string.length()).forEach(i -> {
            char c = string.charAt(i);
            unicode.append("\\u").append(Integer.toHexString(c));
        });

        return unicode.toString();
    }

    /**
     * 将Unicode字符进行解码成string
     *
     * @param unicode Unicode编码字符串
     * @return string
     */
    public static String unicode2String(String unicode) {
        String[] hex = unicode.split("\\\\u");
        return IntStream.range(1, hex.length).map(i -> Integer.parseInt(hex[i], 16))
                .mapToObj(data -> String.valueOf((char) data)).collect(Collectors.joining());
    }

    /**
     * 将以英文逗号相隔的字符串转化为Long类型的集合
     *
     * @param string 字符串
     * @return list集合
     */
    public static List<Long> sting2array(String string) {
        List<Long> list;
        String[] split = string.split(",");
        list = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        return list;
    }
}
