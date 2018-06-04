package com.xx.watermelon.user.cache;

/**
 * @version 1.0, Created by xiesx on 2018-05-24 10:22.
 * @description IUserCacheService 2018/5/24
 * @copyright 2018-05-24 10:22
 */
public interface IUserCacheService {
    /**
     * 获取缓存的值
     * @param cacheKey
     * @return
     */
     String getStringValue(String cacheKey);

    /**
     * 获取一个int类型的数据
     * @param cacheKey
     * @return
     */
     Integer getIntegerValue(String cacheKey);

    /**
     * 设置缓存值
     * @param key
     * @param value
     */
     void setStringValue(String key, String value);

    /**
     * 设置缓存值并设置有效期
     * @param key
     * @param value
     */
     void setStringValueForTime(String key, String value, long time);

    /**
     * 设置数字类型的值并有有效期
     * @param key
     * @param value
     * @param time
     */
     void setIntegerValueForTime(String key, Integer value, long time);

    /**
     * 删除key值
     * @param key
     */
     void delCacheByKey(String key);

    /**
     * 获取token的有效期
     * @param key
     */
     long getExpireTime(String key);

    /**
     * 指定时间类型---秒
     * @param key
     * @return
     */
     long getExpireTimeType(String key);

    /**
     *
     * @param key---分
     * @return
     */
     long getExpireTimeTypeForMin(String key);

    /**
     * 设置一个自增的数据
     * @param key
     * @param growthLength
     */
     void testInc(String key, Long growthLength);

}
