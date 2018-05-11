package com.zc.travel.common.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

import com.zc.travel.common.page.PageParam;
import com.zc.travel.common.utils.json.JSONUtils;

/**
 * 类型转换工具类
 * 
 * @version:
 */
public class ConvertUtils {
	public enum Type {
		BOOLEAN(new String[] { "boolean", "java.lang.boolean" }), STRING(new String[] { "string", "java.lang.string" }), INT(new String[] {
				"int", "integer", "java.lang.integer" }), LONG(new String[] { "long", "java.lang.long" }), BIGINT(new String[] { "bigint",
				"biginteger", "java.math.biginteger" }), DOUBLE(new String[] { "double", "java.lang.double" }), DATE(new String[] { "date",
				"java.util.date" }), SQLDATE(new String[] { "sqldate", "java.sql.date" }), TIMESTAMP(new String[] { "timestamp",
				"java.sql.timestamp" }), MAP(new String[] { "map" }), LIST(new String[] { "list" }), DECIMAL(new String[] { "decimal",
				"java.math.bigdecimal" });

		private String[] names;

		private Type(String[] names) {
			this.names = names;
		}

		public static Type parseType(String name) {
			if (name != null) {
				name = name.toLowerCase();
			}
			for (Type type : Type.values()) {
				for (String tname : type.names) {
					if (tname.equals(name)) {
						return type;
					}
				}
			}
			return null;
		}

		public static Type parseType(Class clz) {
			if (clz == null) {
				return null;
			}
			return parseType(clz.getName());
		}
	}

	public static Object convert(String toType, String value) {
		Type type = Type.parseType(toType);
		if (type == null) {
			throw new RuntimeException("unsupport type : " + toType);
		}
		return convert(type, value);
	}

	public static Object convert(Class toType, String value) {
		if (toType != null && toType.isEnum()) {
			try {
				return Enum.valueOf(toType, value);
			} catch (Exception e) {
			}
		}

		Type type = Type.parseType(toType);
		if (type == null) {
			throw new RuntimeException("unsupport type : " + toType);
		}
		return convert(type, value);
	}

	public static Object convert(Type toType, String value) {
		CheckUtils.notNull(toType, "toType");
		if (Type.STRING.equals(toType)) {
			return value;
		}
		if (CheckUtils.isEmpty(value)) {
			return null;
		}
		switch (toType) {
		case INT:
			return Integer.parseInt(value);
		case LONG:
			return Long.parseLong(value);
		case BIGINT:
			return new BigInteger(value);
		case DOUBLE:
			return Double.parseDouble(value);
		case DATE: {
			try {
				return DateUtils.parseDate(value, new String[] { DateUtils.DATE_FORMAT_DATEONLY, DateUtils.DATE_FORMAT_DATETIME });
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		case SQLDATE: {
			try {
				return new java.sql.Date(DateUtils.parseDate(value, DateUtils.DATE_FORMAT_DATEONLY).getTime());
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		case TIMESTAMP: {
			try {
				return new java.sql.Timestamp(DateUtils.parseDate(value, DateUtils.DATE_FORMAT_DATETIME).getTime());
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		case BOOLEAN:
			return Boolean.parseBoolean(value);
		case DECIMAL:
			return new BigDecimal(value);
			// TODO
		case MAP: {
			return JSONUtils.jsonToMap(value, String.class, String.class);
		}
		case LIST: {
			return JSONUtils.jsonToList(value, String.class);
		}
		default:
			throw new RuntimeException("unsupport type : " + toType);
		}
	}
	/**
	 * 分页查询参数对象转换Map
	 * @param objs
	 * @return
	 */
	public static Map<String,Object> convertObjToMap(Object...objs){
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		if (null == objs || objs.length<1){
			return hashMap;
		}
		for(Object obj : objs){
			if (null == obj){
				continue;
			}
		    Class clazz = obj.getClass();
		    List<Class> clazzs = new ArrayList<Class>();
		    if(!clazz.equals(Object.class)){
		        clazzs.add(clazz);
		        clazz = clazz.getSuperclass();
		    }else{
		    	return null;
		    }
		     
		    for (Class iClazz : clazzs) {
		    	Field[] fields = iClazz.getDeclaredFields();		    	
		        if(iClazz.equals(PageParam.class)){
		        	int pageSize = 0;
		        	int pageFirst = 0;
		        	for (Field field : fields) {
		        		Object objVal = null;
			            try {
			            	if(field.getModifiers()==16){
			            		continue;
			            	}
			            	field.setAccessible(true);
							objVal = field.get(obj);							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
		        		if("numPerPage".equals(field.getName())){
		        			pageSize = Integer.parseInt(objVal.toString());
		        		}
		        		if("pageNum".equals(field.getName())){
		        			pageFirst = Integer.parseInt(objVal.toString());
		        		}
		        	}		        	
		        	hashMap.put("pageSize", pageSize);
		        	hashMap.put("pageFirst", (pageFirst - 1) * pageSize);
		        	hashMap.put("startRowNum", (pageFirst - 1) * pageSize);
		        	hashMap.put("endRowNum", pageFirst * pageSize);
			    }else{
		        	Class cl = iClazz;
		        	List<Field> superFieldList = new ArrayList<>();
		        	while (null!=cl.getSuperclass()){
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
						if(null!=objVal && !"".equals(objVal)){
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
			            if(null!=objVal && !"".equals(objVal)){
							hashMap.put(field.getName(), objVal);
						}
			        }
			    }
		    }
		}
	     
	    return hashMap;
	}
}
