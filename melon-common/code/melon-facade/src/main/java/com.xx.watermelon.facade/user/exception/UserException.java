package com.zc.travel.user.exception;

import com.zc.travel.common.exceptions.BaseCoreException;

/**
 * @description 用户管理服务异常处理类
 * @version 1.0,2017-2-27 下午4:47:42,Liugl,Ins
 * @remark
 */
public class UserException extends BaseCoreException {

	private static final long serialVersionUID = -5202395960278521714L;

	public UserException(String code) {
		super(code);
	}
	
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
	 * 会员支付密码错误
	 */
	public static final String MEMBER_PAYPWD_ERROR = "member.paypwd.error";
	/**
	 * 账号名已存在
	 */
	public static final String MEMBER_NAME_EXISTS = "member.name.exists";
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
	 * 会员已绑定第3方账号
	 */
	public static final String MEMBER_BINDING_THIRD_ACC = "member.binding.third.acc";
	/**
	 * 第3方账号已绑定其它会员
	 */
	public static final String BINDING_THIRD_ACC_MEMBER = "member.other.binding.third.acc";
	
	/**
	 * 资源名称不能为空
	 */
	public static final String SOURCE_SOURCENAME_ISNULL = "source.sourcename.isnull";
	/**
	 * 店铺资质模型字段已存在
	 */
	public static final String CRMSHOPSERVICE_EXISTSMODELFIELDS= "shop.modelfields.exists";
	/**
	 * 手机区号错误
	 */
	public static final String MOBILE_AREA_CODE_WRONG= "mobile.area.code.wrong";
	/**
	 * 手机号码格式错误
	 */
	public static final String MOBILE_FORMAT_WRONG= "mobile.format.wrong";
}
