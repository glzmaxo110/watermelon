package com.zc.travel.dto;

import com.zc.travel.enums.AliyunTextContentFilterResult;

import java.io.Serializable;

public class AliyunTextContentFilterDto  implements Serializable,Cloneable{

    /**
     * 返回代码
     */
    private AliyunTextContentFilterResult aliyunTextContentFilterResult;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 处理建议
     */
    private String suggestion;

    public AliyunTextContentFilterResult getAliyunTextContentFilterResult() {
        return aliyunTextContentFilterResult;
    }

    public void setAliyunTextContentFilterResult(AliyunTextContentFilterResult aliyunTextContentFilterResult) {
        this.aliyunTextContentFilterResult = aliyunTextContentFilterResult;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
