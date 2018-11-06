package com.xx.watermelon.common.util;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description: 针对hbase的entity操作工具
 * @author:
 * @createTime: 2018-08-24 上午10:43
 * @version: 1.0.0
 * @Copyright:
 * @modify:
 **/
public abstract class EntityUtils<T, KEY extends Serializable> {
    /**
     * 删除Map中value为空值
     *
     * @param map 对象
     * @return map
     */
    public static Map removeEmptyForValues(Map<String, Object> map) {

        if (MapUtils.isEmpty(map)) {
            return null;
        }
        map.entrySet().stream().filter(entry -> entry.getValue() == null || entry.getValue().equals(""))
                .map(Map.Entry::getKey).forEach(map::remove);
        return map;
    }

    /**
     * 删除map中key和value出现空的时候
     *
     * @param map 对象
     * @return map
     */
    public static Map removeEmptyForMap(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        map.forEach((key, value) -> {
            if (StringUtils.isEmpty(key)) {
                map.remove(key);
                return;
            }
            if (value == null) {
                map.remove(key);
            }
        });
        return map;
    }

    /**
     * 将包含family-entity 转换为family-map
     *
     * @param map 集合
     * @return map
     */
    public static Map transformEntity2Map(Map<String, Object> map) throws Exception {

        if (MapUtils.isEmpty(map)) return null;
        Map<String, Map<String, Object>> entity2Map = Maps.newHashMap();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            entity2Map.put(entry.getKey(), convertObjectToMap(entry.getValue()));
        }
        return entity2Map;
    }

    /**
     * 将对象转换为Map
     *
     * @param obj 对象
     * @return map
     */
    public static Map<String, Object> convertObjectToMap(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Object> tMap = Maps.newHashMap();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName().replaceFirst(field.getName().substring(0, 1), field.getName()
                    .substring(0, 1).toUpperCase());
            Method method = obj.getClass().getMethod("get" + name);
            tMap.put(field.getName(), method.invoke(obj));
        }
        return tMap;
    }
}

