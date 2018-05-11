package com.zc.travel.common.utils.email;

import java.io.Serializable;

/**
 * @description 邮件发送实体
 * @remark
 */
public class EmailSendEntity implements Serializable {
	
	private static final long serialVersionUID = 6683601413094860270L;
	/**
	 * SMTP主机
	 */
	private String smtp;
	/**
	 * 发件人邮箱号
	 */
	private String sender;
	/**
	 * 发件人密码
	 */
	private String senderPwd;
	/**
	 * 收件人
	 */
	private String recipients;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String body;
	
	/**
	 * SMTP主机
	 */
	public String getSmtp() {
		return smtp;
	}
	/**
	 * SMTP主机
	 */
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	/**
	 * 发件人邮箱号
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * 发件人邮箱号
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * 发件人密码
	 */
	public String getSenderPwd() {
		return senderPwd;
	}
	/**
	 * 发件人密码
	 */
	public void setSenderPwd(String senderPwd) {
		this.senderPwd = senderPwd;
	}
	/**
	 * 收件人
	 */
	public String getRecipients() {
		return recipients;
	}
	/**
	 * 收件人
	 */
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	/**
	 * 邮件主题
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 邮件主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 邮件内容
	 */
	public String getBody() {
		return body;
	}
	/**
	 * 邮件内容
	 */
	public void setBody(String body) {
		this.body = body;
	}
}
