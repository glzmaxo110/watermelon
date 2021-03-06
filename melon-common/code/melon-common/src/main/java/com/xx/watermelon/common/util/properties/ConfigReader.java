package com.xx.watermelon.common.util.properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义PropertyPlaceholderConfigurer返回properties内容
 */
public class ConfigReader extends PropertyPlaceholderConfigurer {

	private static Map<String, Object> ctxPropertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	/**
	 * 外部调用接
	 * <br>
	 * 根据属性名读取属性值
	 * @param name
	 * @return
	 */
	public static String getContextProperty(String name) {
		if(ctxPropertiesMap == null){
			return null;
		}
		Object o= ctxPropertiesMap.get(name);
		if(o==null){
			return null;
		}
		return (String)o ;
	}
	
	/**
	 * 获取值列表
	 * @return
	 */
	public static Map<String, Object> getProperties(){
		return ctxPropertiesMap;
	}
}