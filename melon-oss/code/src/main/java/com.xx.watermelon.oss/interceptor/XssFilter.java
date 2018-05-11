package com.zc.travel.webboss.interceptor;

import com.zc.travel.common.utils.StringUtils;
import com.zc.travel.consts.ConfigVarNames;
import com.zc.travel.sys.mapper.BaseBusinessConfigMapper;
import com.zc.travel.sys.provider.IBaseBusinessConfigReadProvider;
import com.zc.travel.webboss.utils.SpringContextUtil;
import com.zc.travel.webboss.utils.XssHttpServletRequestWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * @description
 * @copyright 2017年9月2日 @
 * @version 1.0,2017年9月2日 上午11:21:25,Ins
 * @remark
 */
public class XssFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(XssFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		try {
			boolean bool = false;
			//跨站请求伪造
			final String referer = httpServletRequest.getHeader("referer");
			String referIp = null;
			if (StringUtils.isNotBlank(referer)) {
				referIp = new URL(referer).getHost();
			} else {
				chain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest), response);
				return;
			}
			bool = checkRefererWhiteList(referIp);
			bool = true;
			if (!bool) {
				LOG.error("####ERROR: 请求数据非法，XssFilter进行数据拦截!");
				httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
				httpServletResponse.setCharacterEncoding("UTF-8");
				httpServletResponse.sendError(404);
			} else {
				// 请求正常
				chain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest), response);
			}
		} catch (Exception ex) {
			LOG.error("####ERROR: XssFilter数据拦截处理异常! 原因: " + ex);
			httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.sendError(404);
			return;
		}
	}

	@Override
	public void destroy() {
	}
	//判断referer是否在白名单里
	private boolean checkRefererWhiteList(String refererId) throws Exception {
		//获取缓存数据
		IBaseBusinessConfigReadProvider baseBusinessConfigReadProvider = SpringContextUtil.getBean("baseBusinessConfigReadProvider");
		BaseBusinessConfigMapper configMapper = baseBusinessConfigReadProvider.getByConfigVarName(ConfigVarNames.REFERER_WHITE_LIST);
 		if (null == configMapper || StringUtils.isEmpty(configMapper.getConfigValue())) {
			return true;
		}
		if (Arrays.asList(configMapper.getConfigValue().split(";")).contains(refererId)) {
			return true;
		}
		return false;
	}
}
