package com.xx.watermelon.common.exception;


import com.xx.watermelon.common.util.properties.ConfigReader;

/**
 * 基础的异常类（所有异常继承这个）
 */
public class BaseCoreException extends Exception {

	private static final long serialVersionUID = 8511925046009944545L;
	/**
	 * 数据记录对象不能为空
	 */
	public static final String DT_RECORD_NOT_NULL = "db.error.dtRecordNotNull";
	/**
	 * 数据记录存入操作失败
	 */
	public static final String INSERT_FAILED = "db.error.insertFailed";
	/**
	 * 数据记录更新操作失败
	 */
	public static final String UPDATE_FAILED = "db.error.updateFailed";
	/**
	 * 数据记录删除操作失败
	 */
	public static final String DEL_FAILED = "db.error.delFailed";
	/**
	 * 违法的TOKEN
	 */
	public static final String TOKEN_IS_ILLICIT = "db.error.tokenIsIllicit";
	
	/**
	 * 参数为空
	 */
	public static final String PARAMS_ISNULL = "params.isnull";
	/**
	 * 参数非法
	 */
	public static final String PARAMS_ERROR = "params.error";
	/**
	 * session为空
	 */
	public static final String SESSION_ISNULL = "session.isnull";

	private String code;
	private String msg;

	public BaseCoreException(String code) {
		super(ConfigReader.getContextProperty(code) == null ? code : ConfigReader.getContextProperty(code));
		this.code = code;
		this.msg = ConfigReader.getContextProperty(code) == null ? code : ConfigReader.getContextProperty(code);
	}

	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

}
