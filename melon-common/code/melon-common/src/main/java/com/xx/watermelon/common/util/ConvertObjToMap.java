package com.xx.watermelon.common.util;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @description: obj转Map
 * @author:
 * @createTime: 2018-07-06 16:50
 * @version: 1.0.0
 * @Copyright:
 * @modify:
 **/

public class ConvertObjToMap {
    public static Map<String, Object> toMap(Object obj) {
        Map<String, Object> hashMap = new HashMap<>();


        Class cl = obj.getClass();
        Field[] fields = cl.getDeclaredFields();
        List<Field> superFieldList = new ArrayList<>();
        while (null != cl.getSuperclass()) {
            Field[] superFields = cl.getSuperclass().getDeclaredFields();
            Collections.addAll(superFieldList, superFields);
            cl = cl.getSuperclass();
        }
        //父类属性
        for (Field field : superFieldList) {
            Object objVal = null;
            field.setAccessible(true);
            try {
                objVal = field.get(obj);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null != objVal && !"".equals(objVal)) {
                hashMap.put(field.getName(), objVal);
            }
        }
        //自身属性
        for (Field field : fields) {
            Object objVal = null;
            field.setAccessible(true);
            try {
                objVal = field.get(obj);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null != objVal && !"".equals(objVal)) {
                hashMap.put(field.getName(), objVal);
            }
        }

        return hashMap;
    }
}
