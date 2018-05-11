package com.zc.travel.consts;

import java.io.Serializable;

/**
 * @description 
 * @version 1.0,2017-4-15 下午3:13:45,Denghs,Ins
 * @remark
 */
public class ConfigVarNames implements Serializable{
	
	private static final long serialVersionUID = 3959374691677527872L;
	
	private ConfigVarNames(){}

	/**
	 * 邮件发送POP3
	 */
	public static final String MAIL_POP3 = "mail.pop3";
	/**
	 * 邮件发送STMS
	 */
	public static final String MAIL_STMS = "mail.stms";
	/**
	 * 邮件发送者邮件
	 */
	public static final String MAIL_SENDER = "mail.sender";
	/**
	 * 邮件发送是否鉴权
	 */
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	/**
	 * 邮件发送超时时间
	 */
	public static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
	/**
	 * 邮件发送编码格式
	 */
	public static final String MAIL_ENCODING = "mail.encoding";
	/**
	 * 邮件发送者密码
	 */
	public static final String MAIL_PASSWORD = "mail.password";
	/**
	 * 邮件验证失败跳转页面
	 */
	public static final String MAIL_CHECK_ERROR_FORWARD = "mail.check.error.forward";
	/**
	 * 邮件链接失效时间，默认2小时
	 */
	public static final String MAIL_LINK_OUTTIME = "mail.link.outtime";
	
	/**
	 * 会员登录密码错误次数
	 */
	public static final String MEMBER_LOGIN_PASSWORD_ERROR_TIME = "member.login.password.error.time";
	/**
	 * 会员支付密码错误次数
	 */
	public static final String MEMBER_PAY_PASSWORD_ERROR_TIME = "member.pay.password.error.time";
	/**
	 * 第三方个人身份证实名验证验证接口APPKEY
	 */
	public static final String USER_IDENTITY_VERIFY_APPKEY = "user.identity.verify.appkey";
	/**
	 * 第三方个人身份证实名验证验证接口地址
	 */
	public static final String USER_IDENTITY_VERIFY = "user.identity.verify";
	/**
	 * 第三方个人身份证实名验证24小时内可使用次数
	 */
	public static final String USER_IDENTITY_VERIFY_DAY_TOTALCOUNT = "user.identity.verify.day.totalCount";
	/**
	 * 第三方个人身份证实名验证开关
	 */
	public static final String USER_IDENTITY_VERIFY_OPEN_OR_NOT	 = "user.identity.verify.open.or.not";
	/**
	 * 账号重置初始密码	
	 */
	public static final String MEMBER_RESET_PASSWORD = "member.reset.password";
	/**
	 * 推送服务APPID
	 */
	public static final String PUSH_APP_APPID="push.app.appid";
	/**
	 * 推送服务APPKEY
	 */
	public static final String PUSH_APP_APPKEY="push.app.appkey";
	/**
	 * 推送服务服务端API鉴权码
	 */
	public static final String PUSH_APP_MASTERSECRET="push.app.masterSecret";
	/**
	 *
	 */
	public static final String PUSH_APP_MEMORYTIME="push.app.memoryTime";
	/**
	 * 推送消息显示logo
	 */
	public static final String PUSH_APP_LOGO="push.app.logo";
	/**
	 * 店铺初始发布次数
	 */
	public static final String SHOPPUBLISHTIME="shopPublishTime";
	/**
	 * N个店铺子会员增加一次发布次数
	 */
	public static final String SHOPMEMBERNUMBERTIME="shopMemberNumberTime";
	/**
	 * 跨站请求伪造白名单
	 */ 
	public static final String REFERER_WHITE_LIST = "referer.white.list";
	/**
	 * 跨站请求伪造白名单
	 */ 
	public static final String API_WHITE_LIST = "api.white.list";
	/**
	 * 店铺资质待续期阈值
	 */
	public static final String SHOP_LICENSE_CONTINUED_PERIOD="shop.license.continued.period";
	/**
	 * pc端店铺前台地址
	 */
	public static final String SHOP_FRONT_URL_PC="shop.front.url.pc";
	/**
	 * 会员/操作员数字邀请码长度
	 */
	public static final String MEMBER_INVITECODE_LENGTH="member.inviteCode.length";
	/**
	 * 短信发送间隔
	 */
	public static final String SMS_SEND_INTERVAL="sms.send.interval";
	/**
	 * 24小时内短信发送总数
	 */
	public static final String SMS_SEND_DAY_TOTALCOUNT="sms.send.day.totalCount";
	/**
	 * 1小时内短信发送总数
	 */
	public static final String SMS_SEND_HOUR_TOTALCOUNT="sms.hour.totalCount";
	/**
	 * PC登录二维码失效时间
	 */
	public static final String LOGIN_QRCODE_INVALID_TIME="login.qrCode.invalid.time";
	/**
	 * PC登录二维码链接
	 */
	public static final String LOGIN_QRCODE_URL="login.qrCode.url";
	/**
	 * 身份证年龄限制
	 */
	public static final String IDENTITY_AGE_LIMIT="identity.age.limit";
	/**
	 * 虚拟供应商Id
	 */
	public static final String VIRTUAL_SUPPLIER_ID="virtualSupplierId";
	/**
	 * 会员邀请二维码链接
	 */
	public static final String MEMBER_INVITECODE_URL="member.invitecode.url";
	/**
	 * 图片添加水印配置
	 */
	public static final String IMAGE_ADD_WATERMARK_PARAMS="image.add.watermark.params";
	/**
	 * 通过IP地址获取地址信息接口地址
	 */
	public static final String IP_GET_ADDRESS_URL="ip.get.address.url";
	/**
	 * 资质默认有效期
	 */
	public static final String QUALITY_DEFAULT_VALIDITY_PERIOD="quality.default.validity.period";

}
