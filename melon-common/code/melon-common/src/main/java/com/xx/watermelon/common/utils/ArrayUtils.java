package com.xx.watermelon.common.utils;

import com.xx.watermelon.common.utils.string.StringUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @description 数组工具类
 * @copyright 2017年11月9日 @ 金锡科技
 * @version 1.0,2017年11月9日 下午7:06:09,Liugl,Ins
 * @remark
 */
public class ArrayUtils {

	/**
	 * 字符串数组值去重，并实现排序
	 * @param arr
	 * @return
	 * @version 1.0,2017年11月9日 下午7:09:56,Liugl,Ins
	 */
	public static String[] removeDuplicate(String[] arr) {
		if (null == arr || arr.length == 0)
			return null;
		Set<String> set = new LinkedHashSet<>();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtils.isNotBlank(arr[i]))
				set.add(arr[i]);
		}
		return set.toArray(new String[set.size()]);
	}

	/**
	 * List去重
	 * @param list
	 * @return
	 * @version 1.0,2017年11月10日 下午4:25:04,Liugl,Ins
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicate(List list) {
		if (CollectionUtils.isEmpty(list))
			return null;
		Set h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}
	
}
