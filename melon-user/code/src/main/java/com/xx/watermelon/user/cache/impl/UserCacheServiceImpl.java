package com.xx.watermelon.user.cache.impl;

import com.xx.watermelon.user.cache.IUserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0, Created by xiesx on 2018-05-24 10:22.
 * @description IRedisServiceImpl 2018/5/24
 * @copyright 2018-05-24 10:22
 */
@Service
public class UserCacheServiceImpl implements IUserCacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getStringValue(String cacheKey){
        return  (String)redisTemplate.opsForValue().get(cacheKey);
    }

    @Override
    public Integer getIntegerValue(String cacheKey) {
        return  (Integer) redisTemplate.opsForValue().get(cacheKey);
    }

    @Override
    public void setStringValue(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setStringValueForTime(String key, String value, long time){
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void setIntegerValueForTime(String key, Integer value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void delCacheByKey(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
        redisTemplate.opsForHash().delete("");
    }

    @Override
    public long getExpireTime(String key){
        return redisTemplate.getExpire(key);
    }
    @Override
    public long getExpireTimeType(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    @Override
    public long getExpireTimeTypeForMin(String key){
        return redisTemplate.getExpire(key,TimeUnit.MINUTES);
    }

    @Override
    public void testInc(String key,Long growthLength){
        redisTemplate.opsForValue().increment(key, growthLength);
    }

}
