package com.zc.travel.webboss.utils.kindeditor;

import java.io.Serializable;

/**
 * @description 模拟表单key-value结构组装实体
 * @copyright 2017年9月12日 @ 金锡科技
 * @version 1.0,2017年9月12日 上午9:52:38,Majl,Ins
 * @remark
 */
public class FormFieldKeyValuePair implements Serializable {

	private static final long serialVersionUID = 985871359842206113L;
	private String key;
	private String value;

	public FormFieldKeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
