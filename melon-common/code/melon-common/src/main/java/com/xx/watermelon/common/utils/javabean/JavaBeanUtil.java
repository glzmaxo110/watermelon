package com.zc.travel.common.utils.javabean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @version 1.0,2017-3-3 下午4:25:17,Denghs,Ins
 * @remark
 */
public class JavaBeanUtil {

	/**
	 * 把javaBean的属性名与值转成Map的key-value
	 * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!"class".equals(key)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("transBean2Map Error {}", e);
		}
		return map;

	}
}
