package com.zc.travel.common.utils;

import com.zc.travel.common.utils.annotations.CopyBean;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0, Created by danvid on 2017-07-21 19:24.
 * @description CustomCopyPropertiesUtil 2017/7/21
 * 自定义属性传递 工具类,配合 注解:CopyBean使用
 */
public class CustomCopyPropertiesUtil {
    /**
     *
     * @param source
     * @param target
     * @return 返回false则复制失败
     */
    public static boolean copyProperties(Object source,Object target){
        try {
            if (null == source || null == target)
                throw new Exception("源对象或目标对象为空");
            Map<String, Object> sourceMap = buildBeanNameMap(source.getClass(),target.getClass());
            Map<String, Object> sourceValueMap = new HashMap<>();
            PropertyDescriptor[] sourcePds = Introspector.getBeanInfo(source.getClass(), Object.class).getPropertyDescriptors();
            for (PropertyDescriptor pd : sourcePds) {
                if (null != sourceMap.get(pd.getName()))
                    sourceValueMap.put((String) sourceMap.get(pd.getName()), pd.getReadMethod().invoke(source));
            }
            PropertyDescriptor[] targetPds = Introspector.getBeanInfo(target.getClass(), Object.class).getPropertyDescriptors();
            for (PropertyDescriptor pd : targetPds) {
                if (sourceValueMap.containsKey(pd.getName()))
                    pd.getWriteMethod().invoke(target, sourceValueMap.get(pd.getName()));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取所有子类和父类字段
     * @param source
     * @param target
     * @return
     */
    public static Map<String,Object> buildBeanNameMap(Class source,Class target) throws Exception{

        Map<String, Object> beanMap = new HashMap<>();
        for(;!source.getName().equals(Object.class.getName());source=source.getSuperclass()){
            Field[] fields = source.getDeclaredFields();
            for (Field field : fields) {
                String value=field.getName();
                CopyBean annotation = field.getAnnotation(CopyBean.class);
                if (null!=annotation){
                    if (!annotation.targetClass().getName().equals(target.getName()))
                        throw new Exception("目标对象与注解配置的不一致");
                    if (annotation.excluded())
                        continue;
                    if (StringUtils.isBlank(annotation.value()))
                        throw new Exception("非排除属性,注解目标属性名数据为空");
                    value=annotation.value();
                }
                beanMap.put(field.getName(),value);
            }
        }
        return beanMap;
    }
}
