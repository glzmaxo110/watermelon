package com.zc.travel.common.web.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.zc.travel.common.utils.cache.redis.RedisUtils;
import com.zc.travel.common.utils.cache.redis.SerializeUtils;
import com.zc.travel.common.web.annotation.Permission;
import com.zc.travel.common.web.constant.PermissionConstant;
import com.zc.travel.common.web.utils.WebUtil;

/**
 * 自定义的细粒度权限拦截.
 * 
 * @author WuShuicheng(吴水成).
 * @version 1.0, 2013-4-2,上午3:58:26.
 */
public class CachePermissionInterceptor implements MethodInterceptor {

	private static final Log log = LogFactory.getLog(CachePermissionInterceptor.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {

		// 判断该方法是否加了@Permission注解
		if (invocation.getMethod().isAnnotationPresent(Permission.class)) {
			// 得到方法上的Permission注解
			final Permission pm = invocation.getMethod().getAnnotation(Permission.class);
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
			final String GWID = WebUtil.getCookieByName(request, "GWID");
			// 获取被注解方法中的request参数，要求方法中一定要有HttpServletRequest参数
			Map<String, Object> userInfoMap = null;
			if (GWID != null) {
				Set<byte[]> keys = RedisUtils.keys(PermissionConstant.WEB_OPERATOR_KEY + GWID);
				Iterator<byte[]> it = keys.iterator();
				while (it.hasNext()) {
					byte[] keyByte = it.next();
					userInfoMap = (Map<String, Object>) RedisUtils.get(SerializeUtils.unSerialize(keyByte));
				}
			}

			// 如果为空，则跳出
			if (userInfoMap == null) {
				return "permissionError";
			}
			@SuppressWarnings("unchecked")
			final List<String> permissions = (List<String>) userInfoMap.get(PermissionConstant.ACTIONS_SESSION_KEY);
			if (permissions.contains(pm.value())) { // 拥有此功能点权限
				// 执行被拦截的方法，如果此方法不调用，则被拦截的方法不会被执行
				log.info("== contain permission:" + pm.value());
				return invocation.proceed();
			}
			// 没有此功能点权限
			log.warn("=== 您没有执行此操作的权限:" + pm.value());
			return "permissionError"; // 跳转到错误提示页面.
		}
		// 没加@Permission注解的方法可直接执行
		// 执行被拦截的方法，如果此方法不调用，则被拦截的方法不会被执行
		return invocation.proceed();
	}
}
