package com.zc.travel.common.web.taglib;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.zc.travel.common.utils.cache.redis.RedisUtils;
import com.zc.travel.common.utils.cache.redis.SerializeUtils;
import com.zc.travel.common.web.constant.PermissionConstant;
import com.zc.travel.common.web.utils.WebUtil;

/**
 * 
 * @描述: 自定义权限权标签 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-17,下午4:04:24 .
 * @版本: 1.0 .
 */
@SuppressWarnings("serial")
public class CachePermissionTaglib extends BodyTagSupport {

	// private static final Log log = LogFactory.getLog(PermissionTaglib.class);

	private String value; // 权限值

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {

		// 取GWID
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String GWID = WebUtil.getCookieByName(request, "GWID");

		Map<String, Object> userInfoMap = null;

		if (StringUtils.isNotBlank(value)) {
			try {
				Set<byte[]> keys = RedisUtils.keys(PermissionConstant.WEB_OPERATOR_KEY + GWID);
				Iterator<byte[]> it = keys.iterator();
				while (it.hasNext()) {
					byte[] keyByte = it.next();
					userInfoMap = (Map<String, Object>) RedisUtils.get(SerializeUtils.unSerialize(keyByte));
				}

				if (userInfoMap == null) {
					return SKIP_BODY;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			final List<String> permissions = (List<String>) userInfoMap.get(PermissionConstant.ACTIONS_SESSION_KEY);
			if (permissions.contains(value.trim())) { // 拥有此功能点权限
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
