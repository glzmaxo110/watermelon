package com.zc.travel.common.utils.number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @version 1.0, 2017-3-28 上午10:30:03,Liugl,Ins
 * @description 数字处理帮助类
 * @remark
 */
public class NumberUtil {

    /**
     * 判断字符串是否为数字（负数、零、正数），不包括小数
     *
     * @param str
     * @return true-是；false-否
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isBlank(str))
            return false;
        if (str.indexOf("-") == 0)
            str = str.substring(1);
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 以Integer形式返回指定的值<br>
     * 如果指定的值为null或无法转为Integer形式，将返回指定的<code>defaultValue</code>
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static final Integer getInteger(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return value.getClass() == Integer.class ? (Integer) value : ((Number) value).intValue();
        } else if (value instanceof CharSequence && ((CharSequence) value).length() == 0) {
            return defaultValue;
        } else {
            try {
                return Integer.valueOf(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    /**
     * 根据两个数计算出比例
     *
     * @param a
     * @param b
     * @return
     */
    public static int[] calcRatio(int a, int b) {
        int tmp = a > b ? b : a;
        for (int i = tmp; i > 0; i--) {
            if (a % i == 0 && b % i == 0) {
                return new int[]{(a / i), (b / i)};
            }
        }
        return new int[]{a, b};
    }

}
