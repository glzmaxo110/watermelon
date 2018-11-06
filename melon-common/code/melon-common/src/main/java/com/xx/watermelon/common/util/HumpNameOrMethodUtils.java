package com.xx.watermelon.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @description: 将对象属性转成hbase的列属性
 * @author:
 * @createTime: 2018-08-24 上午10:14
 * @version: 1.0.0
 * @Copyright:
 * @modify:
 **/
public class HumpNameOrMethodUtils {

    private static final String SEPARATOR_UNDER_SCORE = "_";

    /**
     * 用驼峰命名法将参数转换为Entity属性
     *
     * @param param 参数
     * @return 对象属性
     */
    public static String convertVarToEntity(String param) {

        if (StringUtils.isBlank(param)) return "";

        StringBuffer buffer = new StringBuffer();
        param = param.replaceFirst(param.substring(0, 1), param.substring(0, 1).toLowerCase(Locale.US));
        if (param.indexOf(SEPARATOR_UNDER_SCORE) > 0) {
            String[] strings = param.split(SEPARATOR_UNDER_SCORE);
            IntStream.range(0, strings.length).forEach(i -> {
                if (i == 0) {
                    buffer.append(strings[i]);
                } else {
                    buffer.append(str2LowerCase(strings[i]));
                }
            });
        }

        return buffer.toString();

    }

    /**
     * 用驼峰命名法 将Entity属性转换为参数
     *
     * @param param 对象属性
     * @return string   参数
     */
    public static String convertEntityToVar(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        char[] varChar = param.toCharArray();
        int i = 0;
        for (char c : varChar) {
            if (i == 0) {
                buffer.append(String.valueOf(c));
            } else {
                if (compareToLowerCase(String.valueOf(c))) {
                    buffer.append("_").append(String.valueOf(c).toLowerCase());
                } else {
                    buffer.append(String.valueOf(c));
                }
            }
            i++;
        }

        return buffer.toString();
    }

    /**
     * 将首位字符转换为大写
     *
     * @param param 字符串
     * @return string
     */
    private static String str2LowerCase(String param) {
      /*  if (StringUtils.isBlank(param)) {
            return "";
        }
        return param.replaceFirst(param.substring(0, 1), param.substring(0, 1).toUpperCase());*/
        return Optional.ofNullable(param).map(p -> param.replaceFirst(param.substring(0, 1),
                param.substring(0, 1).toUpperCase())).orElse("");
    }

    /**
     * 判断是否字母大写
     *
     * @param source 源字符
     * @return bool
     */
    private static Boolean compareToLowerCase(String source) {

        if (StringUtils.isEmpty(source)) {
            return false;
        }
        return !source.equals(source.toLowerCase(Locale.US));
    }
}
