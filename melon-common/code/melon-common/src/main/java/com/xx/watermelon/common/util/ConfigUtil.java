package com.xx.watermelon.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Optional;

/**
 * @description: 获取属性文件数据工具类(可获取.yml)
 * @author:
 * @createTime: 2018-03-28 下午4:35
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/

public class ConfigUtil {

    @Autowired
    private static Environment env;

    static {
        env = BeanHelper.getBean(Environment.class);
    }

    public static boolean getBoolean(String paramString) {
        return Boolean.valueOf(env.getProperty(paramString, "false"));
    }

    public static int getInt(String paramString) {
        String property = env.getProperty(paramString);
        ///这种写法没有问题
        /*if (StringUtils.isNotBlank(property)) return  Integer.parseInt(property);
        return 0;*/
        return Optional.ofNullable(property).map(p->Integer.valueOf(property.trim())).orElse(0);
    }

    public static int getInt(String paramString, int defaultValue) {
        String property = env.getProperty(paramString);
        ///这种写法没有问题
        /*if (StringUtils.isNotBlank(property)) return  Integer.parseInt(property.trim());
        return 0;*/
       return Optional.ofNullable(property).map(p->Integer.valueOf(property.trim())).orElse(defaultValue);
    }

    public static double getDouble(String paramString, double defaultValue) {
        String property = env.getProperty(paramString);
        /*if (StringUtils.isNotBlank(property)) return  Double.parseDouble(property.trim());
        return 0.0;*/
        return Optional.ofNullable(property).map(p->Double.valueOf(property.trim())).orElse(defaultValue);
    }

    public static long getLong(String paramString) {
       String property = env.getProperty(paramString);
        ///这种写法没有问题
        return Optional.ofNullable(property).map(p->Long.valueOf(property.trim())).orElse(0L) ;
       /*if (StringUtils.isNotBlank(property)) return  Long.parseLong(property.trim());
       return 0L;*/
    }

    public static String getString(String paramString) {
        return env.getProperty(paramString);
    }

    public static String getString(String paramString, String defaultValue) {
         String property = env.getProperty(paramString);
        ///这种写法也行就是有点low了
        /*if (StringUtils.isNotBlank(property)) return property.trim();
        return defaultValue;*/
        return Optional.ofNullable(property).map(p ->property.trim()).orElse(defaultValue);

    }

    public static String[] getStringArray(String paramString) {
       String property = env.getProperty(paramString);
        ///这种写法没有问题
        /*if (StringUtils.isNotBlank(property)) return property.split(",");
        return null;*/
       return new String[]{Optional.ofNullable(property).map(p -> property)
               .orElse(null)};
    }

}
