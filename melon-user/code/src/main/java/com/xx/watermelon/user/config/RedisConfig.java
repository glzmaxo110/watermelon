package com.xx.watermelon.user.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0, Created by xiesx on 2018-06-04 17:40.
 * @description RedisConfig 2018/6/4
 * @copyright 2018-06-04 17:40
 */
@Configuration
@EnableCaching  //触发一个post processor，这会扫描每一个spring bean，查看是否已经存在注解对应的缓存。如果找到了，就会自动创建一个代理拦截方法调用，使用缓存的bean执行处理(需要用到的bean)
public class RedisConfig {



}
