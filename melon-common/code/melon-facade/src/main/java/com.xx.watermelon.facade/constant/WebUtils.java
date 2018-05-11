package com.zc.travel.consts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zc.travel.intercepts.RequestFilter;
import com.zc.travel.user.mapper.CrmResourcesMapper;

/**
 * 提供一些系统中使用到的共用方法
 * 比如获得会员信息,获得后台站点信息
 */
public class WebUtils {

	/**
	 * 获取登录IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		UserSession session = getUserSession(request);
		if (null != session && null != session.getMember()) {
			return session.getIpAddr();
		} else {
			return null;
		}
	}

	/**
	 * 获得当前登录用户ID
	 * @param request
	 * @return
	 */
	public static Long getUserId(HttpServletRequest request) {
		UserSession session = getUserSession(request);
		if (null != session && null != session.getMember()) {
			return session.getMember().getId();
		} else {
			return null;
		}
	}
	
	/**
	 * 获得当前登录用户名称
	 * @param request
	 * @return
	 */
	public static String getUserName(HttpServletRequest request) {
		UserSession session = getUserSession(request);
		if (null != session && null != session.getMember()) {
			return session.getMember().getMemberName();
		} else {
			return null;
		}
	}
	
	/**
	 * 获得用户
	 * @param request
	 * @return
	 */
	public static UserSession getUserSession(HttpServletRequest request) {
		return (UserSession) request.getSession().getAttribute(SessionKeys.CURR_MEMBER);
	}
	
	/**
	 * 设置用户
	 * @param request
	 * @param user
	 */
	public static void setUserSession(HttpServletRequest request, UserSession sessionInfo) {
		request.getSession().setAttribute(SessionKeys.CURR_MEMBER, sessionInfo);
	}
	
	/**
	 * 获得当前用户
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, CrmResourcesMapper> getCurrCrmResources(HttpServletRequest request) {
		Object dataobj = request.getSession().getAttribute(SessionKeys.CURR_MEMBER_RESOURCE);
		//String dataobjJsonStr= JSON.toJSONString(dataobj);
		return (Map<String, CrmResourcesMapper>) dataobj;
	}

	/**
	 * 设置当前用户
	 * @param request
	 * @param user
	 */
	public static void setCurrCrmResources(HttpServletRequest request, Map<String, CrmResourcesMapper> crmResources) {
		//String crmResourcesJsonStr = JSON.toJSONString(crmResources);
		request.getSession().setAttribute(SessionKeys.CURR_MEMBER_RESOURCE, crmResources);
	}


	/**
	 * 检查当前用户是否具有某一项权限
	 * @param authCode
	 * @return
	 */
	public static Boolean checkAuthCode(String authCode){
		HttpServletRequest req = RequestFilter.getRequest();
		Map<String, CrmResourcesMapper> currUserAllAuthList = getCurrCrmResources(req);

		for(Map.Entry<String, CrmResourcesMapper> item:currUserAllAuthList.entrySet()){
			if(item.getValue().getPreCode().equalsIgnoreCase(authCode) || item.getValue().getTargetUrl().equalsIgnoreCase(authCode)){
				return true;
			}
		}


		return false;
	}
	
	/**
	 * 获得当前用户职能
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getCurrCrmDuty(HttpServletRequest request) {
		return (Map<String, Object>) request.getSession().getAttribute(SessionKeys.CURR_MEMBER_DUTY);
	}
	
	/**
	 * 设置当前用户职能
	 * @param request
	 * @param user
	 */
	public static void setCurrCrmDuty(HttpServletRequest request, Map<String, Object> crmDuty) {
		request.getSession().setAttribute(SessionKeys.CURR_MEMBER_DUTY, crmDuty);
	}
	
	/**
	 * 清除用户的登录标记
	 * @param request
	 */
	public static void removeUserSession(HttpServletRequest request) {
		request.getSession().removeAttribute(SessionKeys.CURR_MEMBER);
		request.getSession().invalidate();
	}

}
