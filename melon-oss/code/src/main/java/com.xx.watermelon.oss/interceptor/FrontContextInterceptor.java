package com.zc.travel.webboss.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.zc.travel.consts.UserSession;
import com.zc.travel.consts.WebUtils;
import com.zc.travel.dto.JsonResultDto;

/**
 * 上下文信息拦截器
 * 包括登录信息、权限信息、站点信息
 */
public class FrontContextInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = Logger.getLogger(FrontContextInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		UserSession sessionInfo = (UserSession) WebUtils.getUserSession(request);
		if (sessionInfo != null && null != sessionInfo.getMember() && null != sessionInfo.getMember().getId()) {
			WebUtils.setUserSession(request, sessionInfo);
			return true;
		} else {
			try {
				if (request.getHeader("Accept").contains("application/json")) {
					JsonResultDto errInfo = new JsonResultDto();
					errInfo.setErrCode(-1);
					errInfo.setErrMessage("系统超时，请重新登录！");
					response.setHeader("Content-type","application/json;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					PrintWriter pw = response.getWriter();
					pw.write(JSON.toJSONString(errInfo));
				} else { 
					final String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					String logoutAlert = "<script>alert('系统超时，请重新登录！');window.top.location.href='"+loginUrl+"';</script>";
					response.setHeader("Content-type","text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					PrintWriter pw = response.getWriter();
					pw.write(logoutAlert);
				}
			} catch (IOException e) {
				LOGGER.error("####: 拦截请求跳转到登录页面异常。");
				e.printStackTrace();
			}
			return false;
		}
	}

}