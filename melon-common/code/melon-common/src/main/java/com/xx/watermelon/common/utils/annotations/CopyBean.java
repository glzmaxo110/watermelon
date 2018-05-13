package com.xx.watermelon.common.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 1.0, Created by danvid on 2017-07-21 17:08.
 * @description BeanCopy 2017/7/21
 * @copyright 2017-07-21 17:08 @ 金锡科技
 * 不同属性名,传递属性使用 注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyBean {
    /**
     * 目标字段
     * @return
     */
    String value() default "";

    /**
     * 目标Class
     * @return
     */
    Class targetClass();

    /**
     * 是否排除
     * @return
     */
    boolean excluded() default false;
}
