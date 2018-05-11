/**
 * com.zc.travel.integral.mapper.IntegralLogMapper.java
 */
package com.zc.travel.log.mapper;

import com.zc.travel.common.mapper.BaseMapper;

/**
 * @file  IntegralLogMapper.java
 * @author Liugl
 * @version 0.1
 * @IntegralLogMapper实体类
 * Copyright(C), 2013-2014
 *		 Guangzhou Sunrise Technology Co., Ltd.
 * History
 *   	1. Date: 2017-02-24 10:37:48
 *      	Author: Liugl
 *      	Modification: this file was created
 *   	2. ...
 */
public class LogMapper extends BaseMapper {

	private static final long serialVersionUID = -4682680522745249866L;
	/**
	 * 业务系统代码，取SystemCode枚举值
	 */
	private Integer systemCode;
	/**
	 * 操作人ID
	 */
	private java.lang.Long optUserId;
	/**
	 * 操作人名字
	 */
	private java.lang.String memberName;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 关键词
	 */
	private java.lang.String optKeywords;
	/**
	 * 日志内容
	 */
	private java.lang.String content;
	/**
	 * 操作IP
	 */
	private java.lang.String ip;
	/**
	 * 日志状态（1-新建；2-已处理；3-忽略）
	 */
	private java.lang.Integer logStatus;
	/**
	 * 日志类型（1-业务处理日志；2-系统运行日志）
	 */
	private java.lang.Integer logType;
	/**
	 * 日志级别，取LogOptLevel枚举值
	 */
	private java.lang.Integer optLevel;
	/**
	 * 日志操作状态，取LogOptStatus枚举值
	 */
	private java.lang.Integer optStatus;
	/**
	 * 日志操作类型，取LogOptType枚举值
	 */
	private java.lang.Integer optType;

	/**
	 * 业务系统代码，取SystemCode枚举值
	 */
	public Integer getSystemCode() {
		return systemCode;
	}
	/**
	 * 业务系统代码，取SystemCode枚举值
	 */
	public void setSystemCode(Integer systemCode) {
		this.systemCode = systemCode;
	}
	/**
	 * 操作人ID
	 */
	public void setOptUserId(java.lang.Long value) {
		this.optUserId = value;
	}
	/**
	 * 操作人ID
	 */
	public java.lang.Long getOptUserId() {
		return this.optUserId;
	}
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 日志级别
	 */
	public java.lang.Integer getOptLevel() {
		return optLevel;
	}
	/**
	 * 日志级别
	 */
	public void setOptLevel(java.lang.Integer optLevel) {
		this.optLevel = optLevel;
	}
	/**
	 * 日志操作状态
	 */
	public java.lang.Integer getOptStatus() {
		return optStatus;
	}
	/**
	 * 日志操作状态
	 */
	public void setOptStatus(java.lang.Integer optStatus) {
		this.optStatus = optStatus;
	}
	/**
	 * 日志操作类型
	 */
	public void setOptType(java.lang.Integer value) {
		this.optType = value;
	}
	/**
	 * 日志操作类型
	 */
	public java.lang.Integer getOptType() {
		return this.optType;
	}
	/**
	 * 关键词
	 */
	public void setOptKeywords(java.lang.String value) {
		this.optKeywords = value;
	}
	/**
	 * 关键词
	 */
	public java.lang.String getOptKeywords() {
		return this.optKeywords;
	}
	/**
	 * 日志内容
	 */
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	/**
	 * 日志内容
	 */
	public java.lang.String getContent() {
		return this.content;
	}
	/**
	 * 操作IP
	 */
	public void setIp(java.lang.String value) {
		this.ip = value;
	}
	/**
	 * 操作IP
	 */
	public java.lang.String getIp() {
		return this.ip;
	}
	/**
	 * 日志状态（1-新建；2-已处理；3-忽略）
	 */
	public java.lang.Integer getLogStatus() {
		return logStatus;
	}
	/**
	 * 日志状态（1-新建；2-已处理；3-忽略）
	 */
	public void setLogStatus(java.lang.Integer logStatus) {
		this.logStatus = logStatus;
	}
	/**
	 * 日志类型（1-业务处理日志；2-系统运行日志）
	 */
	public java.lang.Integer getLogType() {
		return logType;
	}
	/**
	 * 日志类型（1-业务处理日志；2-系统运行日志）
	 */
	public void setLogType(java.lang.Integer logType) {
		this.logType = logType;
	}

	public String getMemberName() {return memberName;}

	public void setMemberName(String memberName) {this.memberName = memberName;}

	public String toString() {
		  StringBuffer buffer = new StringBuffer();
		  buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
			   buffer.append("Id").append("='").append(getId()).append("' ");
			   buffer.append("SystemCode").append("='").append(getSystemCode()).append("' ");
		       buffer.append("OptUserId").append("='").append(getOptUserId()).append("' ");
			   buffer.append("memberName").append("='").append(getOptUserId()).append("' ");
			   buffer.append("CreateTime").append("='").append(getCreateTime()).append("' ");	
			   buffer.append("OptLevel").append("='").append(getOptLevel()).append("' ");
			   buffer.append("OptStatus").append("='").append(getOptStatus()).append("' ");	
			   buffer.append("OptType").append("='").append(getOptType()).append("' ");
			   buffer.append("OptKeywords").append("='").append(getOptKeywords()).append("' ");	
			   buffer.append("Content").append("='").append(getContent()).append("' ");	
			   buffer.append("Ip").append("='").append(getIp()).append("' ");
			   buffer.append("LogStatus").append("='").append(getLogStatus()).append("' ");
			   buffer.append("LogType").append("='").append(getLogType()).append("' ");
			   buffer.append("Version").append("='").append(getVersion()).append("' ");
			   buffer.append("DeleteStatus").append("='").append(getDeleteStatus()).append("' ");
		  buffer.append("]");
		  return buffer.toString();
	}		

}
