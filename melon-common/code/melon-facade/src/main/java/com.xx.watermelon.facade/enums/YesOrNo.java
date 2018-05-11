package com.zc.travel.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @version 1.0, 2017-2-24 下午6:21:37,Liugl,Ins
 * @description 是否枚举
 * @remark
 */
public enum YesOrNo {
    /**
     * 1、是
     */
    YES(1, "是"),
    /**
     * 2、否
     */
    NO(2, "否");

    private String name;
    private int index;

    private YesOrNo(int index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * 通过下标获得枚举
     */
    public static YesOrNo getByIndex(Integer index) {
        if (null == index)
            return null;
        for (YesOrNo at : YesOrNo.values()) {
            if (at.index == index)
                return at;
        }
        return null;
    }

    /**
     * 通过名称获得枚举
     */
    public static YesOrNo getByName(String name) {
        if (StringUtils.isBlank(name))
            return null;
        for (YesOrNo at : YesOrNo.values()) {
            if (at.name.equals(name))
                return at;
        }
        return null;
    }

    /**
     * 返回值是否为yes
     * @param value
     * @return
     */
    public static boolean yes(int value) {
        return value == YES.index;
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
