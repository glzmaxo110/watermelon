package com.xx.watermelon.user.conts;


import com.xx.watermelon.common.utils.string.StringUtils;
import com.xx.watermelon.user.mapper.CrmMemberMapper;

import java.io.Serializable;

/**
 * @description	用户登录session信息对象
 * @version 1.0,2017-3-9 下午2:06:46,Liugl,Ins
 * @remark
 */
public class UserSession implements Serializable {
	
	private static final long serialVersionUID = -3246190612833378123L;
	/**
	 * 当前登录用户信息
	 */
	private CrmMemberMapper member;
	/**
	 * 当前用户登录IP地址
	 */
	private String ipAddr;
	/**
	 * 登录终端类型（1-PC；2-APP）
	 */
	private Integer terminalType;
	/**
	 * 当前登录用户信息
	 */
	public CrmMemberMapper getMember() {
		return member;
	}
	/**
	 * 当前登录用户信息
	 */
	public void setMember(CrmMemberMapper member) {
		this.member = member;
	}
	/**
	 * 当前用户登录IP地址
	 */
	public String getIpAddr() {
		if (StringUtils.isBlank(ipAddr))
			ipAddr = "0.0.0.0";
		return ipAddr;
	}
	/**
	 * 当前用户登录IP地址
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	/**
	 * 登录终端类型（1-PC；2-APP）
	 */
	public Integer getTerminalType() {
		return terminalType;
	}
	/**
	 * 登录终端类型（1-PC；2-APP）
	 */
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("memberId").append("='").append((member != null ? member.getId() : null)).append("' ");
		buffer.append("ipAddr").append("='").append(getIpAddr()).append("' ");
		buffer.append("]");
		return buffer.toString();
	}
}
