package com.zc.travel.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Java反射获取父类参数帮助类
 * @description
 * @copyright 2017年7月20日 @ 金锡科技
 * @version 1.0,2017年7月20日 下午5:35:43,Liugl,Ins
 * @remark
 */
public class ReflectionUtils {

	/**
	 * 循环向上转型,获取父类方法名
	 * @param object	子类对象
	 * @param methodName父类中的方法名
	 * @param types		父类中的方法参数类型
	 * @return 父类中的方法对象
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>... types) {
		Method method = null;
		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName, types);
				return method;
			} catch (Exception e) {
				// 这里甚么都不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会进入
			}
		}
		return null;
	}

	/**
	 * 直接调用对象方法,而忽略修饰符(private, protected, default)
	 * @param object	子类对象
	 * @param methodName父类中的方法名
	 * @param types		父类中的方法参数类型
	 * @param parameters父类中的方法参数
	 * @return 父类中方法的执行结果
	 */
	public static Object invokeMethod(Object object, String methodName, Class<?>[] types, Object[] parameters) {
		// 根据 对象、方法名和对应的方法参数 通过取 Method 对象
		Method method = getDeclaredMethod(object, methodName, types);
		// 抑制Java对方法进行检查,主要是针对私有方法而言
		method.setAccessible(true);
		try {
			if (null != method) {
				// 调用object 的 method 所代表的方法，其方法的参数是 parameters
				return method.invoke(object, parameters);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 循环向上转型,获取父类字段 
	 * @param object	子类对象
	 * @param fieldName	父类中
	 * @return 父类中字段
	 */
	public static Field getDeclaredField(Object object, String fieldName) {
		Field field = null;
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
				// 这里甚么都不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会进入
			}
		}
		return null;
	}

	/**
	 * 直接设置对象属性值,忽略private/protected修饰符
	 * @param object	子类对象
	 * @param fieldName	父类中
	 * @param value		将要设置的值
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) {
		// 根据 对象和属性名通过取 Field对象
		Field field = getDeclaredField(object, fieldName);
		// 抑制Java对其的检查
		field.setAccessible(true);
		try {
			// 将 object 中 field 所代表的值 设置为 value
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接读的属性值,忽略private/protected修饰符
	 * @param object	子类对象
	 * @param fieldName	父类中
	 * @return			父类中属性值
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		// 根据 对象和属性名通过取 Field对象
		Field field = getDeclaredField(object, fieldName);
		// 抑制Java对其的检查
		field.setAccessible(true);
		try {
			// 获的属性值
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
