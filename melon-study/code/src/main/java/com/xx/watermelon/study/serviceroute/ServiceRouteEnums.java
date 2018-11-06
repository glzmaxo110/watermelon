package com.xx.watermelon.study.serviceroute;

import org.apache.commons.lang.StringUtils;

/**
 * @author xiesx
 * @version 1.0, 2018-11-05 下午6:21:37,xiesx,Ins
 * @description 路由服务枚举
 * @remark
 */
public enum ServiceRouteEnums {
    /**
     * 1、阿里云短信服务
     */
    AliyunMsgService(1, "阿里云短信服务"),

    /**
     * 2、网易短信服务
     */
    WangYiMsgService(2, "网易短信服务"),
    ;

    private String name;
    private int index;

    private ServiceRouteEnums(int index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * 通过下标获得枚举
     */
    public static ServiceRouteEnums getByIndex(Integer index) {
        if (null == index) {
            return null;
        }
        for (ServiceRouteEnums at : ServiceRouteEnums.values()) {
            if (at.index == index) {
                return at;
            }
        }
        return null;
    }

    /**
     * 通过名称获得枚举
     */
    public static ServiceRouteEnums getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (ServiceRouteEnums at : ServiceRouteEnums.values()) {
            if (at.name.equals(name)) {
                return at;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.index + ":" + this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
