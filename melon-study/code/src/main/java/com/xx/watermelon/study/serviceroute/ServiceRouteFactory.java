package com.xx.watermelon.study.serviceroute;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author user
 * @description 利用Spring的BeanFactory获取不同分类产品业务接口实现实例
 * @copyright
 * @version 1.0,2018年11月05日 上午11:59:02
 */
@Configuration
public class ServiceRouteFactory implements BeanFactoryAware {

	private static BeanFactory beanFactory;
	/**
	 * beanMap存储格式说明：key=ServiceRouteEnums.key, value=ServiceRouteEnums.value
	 */
	private static Map<Integer, String> beanMap = null;
	
	static {
		beanMap = new HashMap<Integer, String>(64);
		// 添加需要路由的service
		beanMap.put(ServiceRouteEnums.AliyunMsgService.getIndex(), "aliyunMsgService");
		beanMap.put(ServiceRouteEnums.WangYiMsgService.getIndex(), "wangYiMsgService");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		ServiceRouteFactory.beanFactory = beanFactory;
	}

	/**
	 * 按供应类目索引ID路由，获取不同分类产品业务接口实现实例
	 * @param indexId	索引ID
	 * @return
	 * @version 1.0,2018年11月05日 下午2:48:59
	 */
	public static Object getBean(Integer indexId) {
		Objects.requireNonNull(indexId, "The parameter [indexId] is empty.");
		Objects.requireNonNull(beanFactory, "The object [beanFactory] is empty.");
		Objects.requireNonNull(beanMap, "The object [beanMap] is empty.");
		
		if (!beanMap.containsKey(indexId)) {
			throw new NullPointerException("The parameter [indexId] does not exist in object [beanMap].");
		}
		return beanFactory.getBean(beanMap.get(indexId));
	}
	
}
