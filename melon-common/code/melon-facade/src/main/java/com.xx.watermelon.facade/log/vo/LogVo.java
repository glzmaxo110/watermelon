package com.zc.travel.log.vo;

import java.io.Serializable;

public class LogVo implements Serializable {
	
	private static final long serialVersionUID = 7609807369326597148L;
	/**
	 * 业务系统代码，取SystemCode枚举值
	 */
	private Integer systemCode;
	/**
	 * 操作人ID
	 */
	private java.lang.Long optUserId;
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
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	public LogVo() {
	}

	/**
	 * @param systemCode 业务系统代码
	 * @param optUserId 操作人ID
	 * @param optKeywords 关键词
	 * @param content 日志内容
	 * @param ip 操作IP地址
	 * @param optStatus 日志操作状态，取LogOptStatus枚举值
	 * @param optType 日志操作类型，取LogOptType枚举值
	 * @param optLevel 日志级别，取LogOptLevel枚举值
	 * @param logType 日志类型（1-业务处理日志；2-系统运行日志）
	 */
	public LogVo(Integer systemCode, Long optUserId, String optKeywords,
			String content, String ip, Integer optStatus, Integer optType,
			Integer optLevel, Integer logType) {
		this.systemCode = systemCode;
		this.optUserId = optUserId;
		this.optKeywords = optKeywords;
		this.content = content;
		this.ip = ip;
		this.optStatus = optStatus;
		this.logType = logType;
		this.optLevel = optLevel;
		this.optType = optType;
	}
	
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
	/**
	 * 开始时间
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 开始时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 结束时间
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
