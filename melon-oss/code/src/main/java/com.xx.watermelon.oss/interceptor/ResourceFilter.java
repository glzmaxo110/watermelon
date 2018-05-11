package com.zc.travel.webboss.interceptor;

import com.alibaba.fastjson.JSON;
import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.consts.UserSession;
import com.zc.travel.consts.WebUtils;
import com.zc.travel.crm.exception.CrmException;
import com.zc.travel.dto.JsonResultDto;
import com.zc.travel.user.mapper.CrmResourcesMapper;
import com.zc.travel.user.provider.ICrmResourcesReadProvider;
import com.zc.travel.webboss.utils.SpringContextUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *    判断用户是否登录,未登录则退出系统
 */
public class ResourceFilter implements Filter {
	
	private static final Logger LOGGER = Logger.getLogger(ResourceFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
    	HttpServletResponse resp = (HttpServletResponse) response;
    	String requestUri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = requestUri.substring(contextPath.length());
		PrintWriter pw = null;
		UserSession sessionInfo = (UserSession) WebUtils.getUserSession(req);
		if (sessionInfo != null && null != sessionInfo.getMember() && null != sessionInfo.getMember().getId()) {
			//判断用户权限
			try {
				if (!checkResource(req, url)) {
					JsonResultDto errInfo = new JsonResultDto();
					errInfo.setErrCode(0);
					errInfo.setErrMessage("用户没有权限，请联系管理员开通相关权限！");
					resp.setHeader("Content-type","application/json;charset=UTF-8");
					resp.setCharacterEncoding("UTF-8");
					pw = response.getWriter();
					pw.write(JSON.toJSONString(errInfo));
				}
			} catch (Exception e) {
				LOGGER.error("####: 用户无权限。");
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
		chain.doFilter(req, resp);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    /**
	 * oss接口权限
	 * @param url
	 * @return
     * @throws BaseCoreException 
     * @throws CrmException 
	 */
	private boolean checkResource(HttpServletRequest request, String url) throws CrmException, BaseCoreException {
		//暂时开放oss权限
		/*boolean bool = false;
		Pattern p = Pattern.compile("[\\d]");
		Matcher matcher = p.matcher(url);
		String result = matcher.replaceAll("");
		//获取缓存数据
		ICrmResourcesReadProvider crmResourcesReadProvider = SpringContextUtil.getBean("crmResourcesReadProvider");
		UserSession sessionInfo =WebUtils.getUserSession(request);
		Map<String, CrmResourcesMapper> crmResourcesMap = crmResourcesReadProvider.getSystemResources(sessionInfo);
		if(null == crmResourcesMap){
			crmResourcesMap = new HashMap<String, CrmResourcesMapper>();
		}
		
		if (crmResourcesMap.containsKey(result)) {
			bool = true;
		}
		
		if (bool) {
			Map<String, CrmResourcesMapper> currCrmResourcesMap = WebUtils.getCurrCrmResources(request);
			if (currCrmResourcesMap.containsKey(result)) {
					return true;
			}
			
			return false;
		}*/
		return true;
	}
}