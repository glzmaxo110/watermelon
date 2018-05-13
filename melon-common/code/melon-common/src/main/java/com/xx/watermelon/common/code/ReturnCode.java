package com.xx.watermelon.common.code;

import com.xx.watermelon.common.utils.properties.ConfigReader;

/**
 * @description 系统返回码定义，需参考returnCode.properties文件
 * @version 1.0,2017-3-13 下午4:34:58,Liugl,Ins
 * @remark
 */
public class ReturnCode {

	/** 00-请求成功 */
	public static final String REQ_SUCCESS = "req.success";
	/** 01-请求失败 */
	public static final String REQ_FAILURE = "req.failure";
	/** 0000-处理成功 */
	public static final String HANDLE_SUCCESS	= "handle.success";
	/** 0001-系统异常 */
	public static final String SYS_ERROR		= "sys.error";
	/** 0002-请求参数为空 */
	public static final String PARAM_ISNULL		= "param.isnull";
	/** 0003-请求参数错误 */
	public static final String PARAM_ERROR		= "param.error";
	/** 0004-请求方法名为空 */
	public static final String METHOD_ISNULL	= "method.isnull";
	/** 0005-请求方法名非法 */
	public static final String METHOD_INVALID	= "method.invalid";
	/** 0006-appKey数据为空 */
	public static final String APPKEY_ISNULL	= "appkey.isnull";
	/** 0007-appKey数据错误 */
	public static final String APPKEY_INVALID	= "appkey.invalid";
	/** 0008-secretKey数据为空 */
	public static final String SECRETKEY_ISNULL	= "secretkey.isnull";
	/** 0009-secretKey数据错误 */
	public static final String SECRETKEY_INVALID	= "secretkey.invalid";
	/** 0010-签名方法数据为空 */
	public static final String SIGN_METHOD_ISNULL	= "signMethod.isnull";
	/** 0011-签名方法数据错误 */
	public static final String SIGN_METHOD_INVALID	= "signMethod.invalid";
	/** 0012-签名数据为空 */
	public static final String SIGN_ISNULL			= "sign.isnull";
	/** 0013-签名数据错误 */
	public static final String SIGN_INVALID			= "sign.invalid";
	/** 0014-token数据为空 */
	public static final String TOKEN_ISNULL			= "token.isnull";
	/** 0015-token失效 */
	public static final String TOKEN_INVALID		= "token.invalid";
	/** 0016-session数据为空 */
	public static final String SESSION_ISNULL		= "session.isnull";
	/** 0017-session失效 */
	public static final String SESSION_INVALID		= "session.invalid";
	/** 0018-账户在其它地方登录，移除session */
	public static final String SESSION_REMOVE		= "session.remove";
	/** 0019-时间戳为空 */
	public static final String TIMESTAMP_ISNULL 	= "timestamp.isnull";
	/** 0020-格式为空 */
	public static final String FORMAT_ISNULL 		= "format.isnull";
	/** 0021-配置数据为空 */
	public static final String SETTING_ISNULL 		= "setting.isnull";

	/**
	 * 查询返回码
	 * @param code
	 * @return
	 */
	public static String getCode(String code) {
		return ConfigReader.getContextProperty(code);
	}
	/**
	 * 查询返回码描述
	 * @param code
	 * @return
	 */
	public static String getMsg(String code) {
		return ConfigReader.getContextProperty(code + ".msg");
	}
}
