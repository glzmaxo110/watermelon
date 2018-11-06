package com.xx.watermelon.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @description: cookie操作辅助类
 * @author:
 * @createTime: 2018-03-28 下午5:43
 * @version: 1.0.0
 * @copyright:
 * @modify:
 **/
public class CookieUtil {

    public static final String AUTH_KEY = "authKey";
    private static final String COOKIE_PATH = "/";
    private static final int COOKIE_TIMEOUT = 30 * 60;

    /**
     * 设置cookies 超时时间30分钟
     *
     * @param response 响应
     * @param name     键
     * @param value    值
     */
    public static void addCookies(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(COOKIE_TIMEOUT); // 设置超时时间30分钟
        response.addCookie(cookie);
    }

    /**
     * 设置cookie 不超时
     *
     * @param response 响应
     * @param name  键
     * @param value 值
     */
    public static void addCookiesNoDue(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(Integer.MAX_VALUE); // 不过期
        response.addCookie(cookie);
    }

    /**
     * 移除cookie
     *
     * @param response 响应
     * @param name     键
     */
    public static void removeCookies(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(0); // 设置超时时间30分钟
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param request 请求
     * @param name 名称
     * @return  字符串
     */
    public static String getCookies(HttpServletRequest request, String name) {
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && name != null) {
                return Arrays.stream(cookies).filter(c -> name.equals(c.getName())).findFirst().map(Cookie::getValue).orElse("");
            }
        }
        return null;
    }

    /**
     * 删除指定的cookie
     *
     * @param response  响应
     * @param cookieName 要删除的cookie
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        saveCookie(response, cookieName, "", 0, false);
    }

    /**
     * 保存cookie 并设置cookie存活的时间
     * @param response 响应
     * @param cookieName 要保存的cookie的名字
     * @param value      cookie中要存放的值
     * @param time       cookie存活时间
     * @param httpOnly  是否是http请求
     */
    public static void saveCookie(HttpServletResponse response, String cookieName, String value, int time,
                                  boolean httpOnly) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(time);// 设置Cookie的存活时间
        cookie.setPath(COOKIE_PATH);
        // cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);// 保存cookie
    }
}
