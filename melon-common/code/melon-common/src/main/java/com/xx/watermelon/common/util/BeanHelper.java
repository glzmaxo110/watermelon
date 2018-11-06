package com.xx.watermelon.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description: Spring容器中获取bean辅助类
 * @author:
 * @createTime: 2018-03-28 下午2:27
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/

@Component
public class BeanHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public  static ApplicationContext getAppContext() {

        return applicationContext;
    }

    public BeanHelper getInstance() {
        return new BeanHelper();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){

        BeanHelper.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return getAppContext().getBean(name);
    }

    public static <T> T getBean(Class<T> t) {
        return getAppContext().getBean(t);
    }
}
