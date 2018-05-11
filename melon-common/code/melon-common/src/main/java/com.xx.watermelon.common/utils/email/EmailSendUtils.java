package com.zc.travel.common.utils.email;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description Java邮件发送服务入口，提供单线程、多线程方式
 * @remark
 */
public class EmailSendUtils implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailSendUtils.class);
	private EmailSendEntity entity;

	/**
	 * 构造函数
	 * @param entity
	 */
	public EmailSendUtils(EmailSendEntity entity) {
		if (null == entity) {
			entity = new EmailSendEntity();
			entity.setSmtp("smtp.mxhichina.com");
		}
		this.entity = entity;
	}

	/**
	 * 邮件发送（多线程方式）
	 */
	@Override
	public void run() {
		sendEmail();
	}
	
	/**
	 * 邮件发送（同步发送）
	 * @param entity
	 */
	public void sendEmail() {
		if (StringUtils.isBlank(entity.getSender()) || StringUtils.isBlank(entity.getSenderPwd())
				|| StringUtils.isBlank(entity.getRecipients())
				|| StringUtils.isBlank(entity.getSubject()) || StringUtils.isBlank(entity.getBody())) {
			LOG.error("####ERROR: Mailing parameters are empty.");
			return;
		}
		LOG.info(">>>Mail sending start...");
		EmailUtils utils = new EmailUtils(entity.getSmtp());
		utils.setFrom(entity.getSender());
		utils.setSubject(entity.getSubject());
		utils.setNamePass(entity.getSender(), entity.getSenderPwd());
		utils.setBody(entity.getBody());
		utils.setTo(entity.getRecipients());
		utils.sendout();
		LOG.info("<<<Mail sending end...");
	}
	
}
