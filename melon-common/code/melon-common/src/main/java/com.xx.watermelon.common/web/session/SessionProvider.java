package com.zc.travel.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session提供者
 */
public interface SessionProvider {
	
	/**
	 * session参数获取
	 * @param request
	 * @param name
	 * @return
	 */
	public Serializable getAttribute(HttpServletRequest request, String name);
	/**
	 * session参数设置
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 */
	public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value);
	/**
	 * 获取当前登录用户sessionId
	 * @param request
	 * @param response
	 * @return
	 */
	public String getSessionId(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 系统注销
	 * @param request
	 * @param response
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response);
}
