package com.xx.watermelon.common.util;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description: 数字格式化辅助类
 * @author:
 * @createTime: 2018-03-29 上午10:05
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/
public class NumberUtil {

    private NumberUtil() {}

    private static final DecimalFormat SECOND_POINT_FORMAT = new DecimalFormat("#.##");

    static {
        SECOND_POINT_FORMAT.applyPattern("0.00");
    }

    /**
     * 将某个数字格式化带有两位小数
     *
     * @param num 数据对象
     * @return 包含2位小数的数字
     */
    public static String secondPointFormat(Object num) {
        try {
            return SECOND_POINT_FORMAT.format(num);
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }

    public static int getRandom(final int max) {
      int  temp = Math.abs(ThreadLocalRandom.current().nextInt());
      return temp % (max + 1);
    }
}
