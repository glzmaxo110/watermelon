package com.zc.travel.consts;

import com.zc.travel.user.mapper.CrmMemberMapper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class UserSessionUtils {
    /**
     * 获取访问者session
     * @param request
     * @return
     */
    public static UserSession getVisitorUserSession(HttpServletRequest request) {
        UserSession session = new UserSession();
        CrmMemberMapper member = new CrmMemberMapper();
        member.setId(0L);
        session.setMember(member);
        session.setIpAddr(UserSessionUtils.getIpAddr(request));
        return session;
    }

    /**
     * 获取系统session
     * @return
     */
    public static UserSession getSysUserSession() {
        UserSession session = new UserSession();
        CrmMemberMapper member = new CrmMemberMapper();
        member.setId(0L);
        session.setMember(member);
        session.setIpAddr("0.0.0.0");
        return session;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
