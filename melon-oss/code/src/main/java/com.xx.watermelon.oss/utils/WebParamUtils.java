package com.zc.travel.webboss.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 前端数据获取转换帮助类
 */
public class WebParamUtils {

	public static Date getDateValue(Object obj,String formatPattern){
		if(obj != null){
			if(obj.getClass() == String.class){
				if("".equals(StringUtils.trim((String) obj))) return null;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
				try {
					return simpleDateFormat.parse((String) obj);
				} catch (ParseException e) {
					e.printStackTrace();
					throw new RuntimeException("Request param formatting DateType error!");
				}
			}
			throw new RuntimeException("Request param type error!");
		} else {
			return null;
		}
	}
	
	public static String getStringValue(Object obj)
	{
		try{
			if(null == obj || !StringUtils.isNotEmpty(obj.toString()))return null;
			obj =  obj.toString().replaceAll("undefined","").trim();
			if(StringUtils.isBlank(obj.toString()))return null;
			return obj.toString();
		}catch(Exception e)
		{
			return null;
		}
	}
	public static String getStringValue(Object obj,String defaultVal){
		if(null == defaultVal)defaultVal = "";
		try{
			if(null == obj)return defaultVal;
			return obj.toString();
		}catch(Exception e)
		{
			return defaultVal;
		}
	}

	public static Integer getIntegerValue(Object obj){
		return getIntegerValue(obj, null);
	}
	public static Integer getIntegerValue(Object obj, Integer defaultVal){
		if(obj != null){
			if(obj.getClass() == Integer.class){
				return (Integer)obj;
			}
			if(obj.getClass() == Long.class){
				return ((Long)obj).intValue();
			}
			if(obj.getClass() == String.class){
				try{
					return Integer.valueOf((String)obj);
				} catch (Exception e) {
					return null != defaultVal ? defaultVal : null;
				}
			}
			return null != defaultVal ? defaultVal : null;
		} else {
			return null != defaultVal ? defaultVal : null;
		}
	}
	
	public static Long getLongValue(Object obj){
		return getLongValue(obj, null);
	}
	public static Long getLongValue(Object obj, Long defaultVal) {
		if(obj != null){
			if(obj.getClass() == Integer.class){
				return ((Integer)obj).longValue();
			}
			if(obj.getClass() == Long.class){
				return (Long)obj;
			}
			if(obj.getClass() == String.class){
				try{
					return Long.valueOf((String)obj);
				} catch (Exception e) {
					return null != defaultVal ? defaultVal : null;
				}
			}
			return null != defaultVal ? defaultVal : null;
		} else {
			return null != defaultVal ? defaultVal : null;
		}
	}
	
	public static double getDoubleValue(Object obj){
		if(obj != null){
			if(obj.getClass() == BigDecimal.class){
				return ((BigDecimal)obj).doubleValue();
			}
			if(obj.getClass() == Integer.class){
				return ((Integer)obj).doubleValue();
			}
			if(obj.getClass() == Double.class){
				return (Double)obj;
			}
			if(obj.getClass() == String.class){
				try{
					return Double.valueOf((String)obj);
				} catch (Exception e) {
					return 0D;
				}
			}
			return 0D;
		} else {
			return 0D;
		}
	}
	
	public static BigDecimal getBigDecimalValue(Object obj){
		if(obj != null && StringUtils.isNotBlank(obj.toString())){
			return new BigDecimal(obj.toString());
		}
		return BigDecimal.ZERO;
	}
	
	public static BigDecimal getBigDecimalValue(Object obj,BigDecimal blankVal){
		if(obj != null && StringUtils.isNotBlank(obj.toString())){
			try {
				return new BigDecimal(obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Request param type error!");
			}
		}
		return blankVal;
	}
	
	/**
	 * 逗号拼接ID转List
	 * @param idsObj
	 * @return
	 */
	public static List<Long> idsToLongList(Object idsObj){
		if(idsObj == null)
			return null;
		String ids = idsObj.toString();
		if(StringUtils.isBlank(ids))
			return null;
		String[] idArr = ids.split(",");
		if(idArr == null || idArr.length <= 0)
			return null;
		List<Long> result = null;
		for(String item:idArr){
			if(StringUtils.isBlank(item))continue;
			try {
			    long convertItem = Long.parseLong(item);
			    if(convertItem > 0){
			    	if(result == null)result = new LinkedList<Long>();
			    	result.add(convertItem);	
			    }
			} catch (NumberFormatException e) {}
		}
		return result;
	}

}
