package com.xx.watermelon.user.exception;


import com.xx.watermelon.common.exception.BaseCoreException;

/**
 * @description 用户管理服务异常处理类
 * @version 1.0,2017-2-27 下午4:47:42,xiesx,Ins
 * @remark
 */
public class UserException extends BaseCoreException {

	private static final long serialVersionUID = -5202395960278521714L;

	/**
	 * 会员注册/新增失败
	 */
	public static final String MEMBER_INSERT_FAILURE = "member.insert.failure";
	/**
	 * 会员信息已存在
	 */
	public static final String MEMBER_EXIST = "member.exist";
	/**
	 * 会员信息不存在
	 */
	public static final String MEMBER_NOT_EXIST = "member.not.exist";
	/**
	 * 会员来源类型为空
	 */
	public static final String MEMBER_SOURCEFROM_ISNULL = "member.sourcefrom.isnull";
	/**
	 * 微信号为空
	 */
	public static final String MEMBER_OPENID_ISNULL = "member.openid.isnull";
	/**
	 * 会员登录名或密码为空
	 */
	public static final String MEMBER_LOGIN_NAME_PWD_ISNULL = "member.login.name.pwd.isnull";
	/**
	 * 会员登录密码为空
	 */
	public static final String MEMBER_LOGINPWD_ISNULL = "member.loginpwd.isnull";
	/**
	 * 会员登录密码错误
	 */
	public static final String MEMBER_LOGINPWD_ERROR = "member.loginpwd.error";
	/**
	 * 会员支付密码为空
	 */
	public static final String MEMBER_PAYPWD_ISNULL = "member.paypwd.isnull";
	/**
	 * 手机号已存在
	 */
	public static final String MEMBER_MOBILE_EXISTS = "member.mobile.exists";
	/**
	 * 邮箱已存在
	 */
	public static final String MEMBER_EMAIL_EXISTS = "member.email.exists";
	/**
	 * 微信号已存在
	 */
	public static final String MEMBER_OPENID_EXISTS = "member.openid.exists";
	/**
	 * 手机号码格式错误
	 */
	public static final String MOBILE_FORMAT_WRONG= "mobile.format.wrong";

	public UserException(String code) {
		super(code);
	}
}
