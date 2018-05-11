package com.zc.travel.webboss.utils;

import static com.zc.travel.common.web.interceptor.ProcessTimeFilter.START_TIME;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zc.travel.consts.SessionKeys;
import com.zc.travel.consts.UserSession;
import com.zc.travel.consts.WebUtils;
import com.zc.travel.webboss.constants.PropertiesConstants;

/**
 * 前台工具类
 */
public class FrontUtils {

	/**
	 * 页面完整地址
	 */
	public static final String LOCATION = "location";
	/**
	 * 上下文路径
	 */
	public static final String BASE_PATH = "basePath";
	/**
	 * 文件预览路径
	 */
	public static final String FILE_PREVIEW_PATH = "filePreviewPath";
	
	/**
	 * 为前台模板设置公用数据
	 * @param request
	 * @param map
	 */
	public static void frontData(HttpServletRequest request, Map<String, Object> map) {
		UserSession sessionInfo = WebUtils.getUserSession(request);
		String location = RequestUtils.getLocation(request);
		Long startTime = (Long) request.getAttribute(START_TIME);
		frontData(map, sessionInfo, location, startTime, request);
	}
	public static void frontData(Map<String, Object> map, UserSession sessionInfo, String location, Long startTime, HttpServletRequest request) {
		if (startTime != null) {
			map.put(START_TIME, startTime);
		}
		if (sessionInfo != null) {
			map.put(SessionKeys.CURR_MEMBER, sessionInfo);
		}
		map.put(LOCATION, location);
		map.put(BASE_PATH, request.getContextPath());
		map.put(FILE_PREVIEW_PATH, PropertiesConstants.FASTDFS_PREVIEW_PATH);
	}

}
