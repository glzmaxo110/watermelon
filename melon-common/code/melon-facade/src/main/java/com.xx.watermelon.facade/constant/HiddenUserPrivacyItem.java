package com.zc.travel.consts;

import java.io.Serializable;
import java.util.Map;

public class HiddenUserPrivacyItem implements Serializable,Cloneable {
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;

    /**
     * 需要处理的字段列表
     */
    private Map<String,Integer> regexFields;

    /**
     * 返回类型：比如：com.zc.travel.dto.SuperUITableListResultDto
     */
    private String returnType;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, Integer> getRegexFields() {
        return regexFields;
    }

    public void setRegexFields(Map<String, Integer> regexFields) {
        this.regexFields = regexFields;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
