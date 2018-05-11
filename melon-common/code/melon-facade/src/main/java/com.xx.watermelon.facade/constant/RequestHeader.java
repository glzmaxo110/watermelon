package com.zc.travel.consts;

import java.io.Serializable;

/**
 * @description api请求公共参数
 * @version 1.0,2017-3-29 下午3:25:17,Liugl,Ins
 * @remark
 */
public class RequestHeader implements Serializable {
	
	private static final long serialVersionUID = 3173584296933781445L;
	/**
	 * 接口名称
	 */
	private String method;
	/**
	 * 系统分配给应用接入Key
	 */
	private String appKey;
	/**
	 * appKey对应的密钥值，密文传输，md5(明文)
	 */
	private String secretKey;
	/**
	 * 签名算法
	 */
	private String signMethod;
	/**
	 * API输入参数签名结果，具体以各API接口为准
	 */
	private String sign;
	/**
	 * 用户调用token获取接口，系统颁发给应用的授权信息。当接口注明：“需要授权”，则此参数必传；“不需要授权”，则此参数不需要传；“可选授权”，则此参数为可选。
	 */
	private String token;
	/**
	 * 时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8.
	 */
	private String timestamp;
	/**
	 * 响应格式。默认为json格式。
	 */
	private String format;
	
	/**
	 * 接口名称
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 接口名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 系统分配给应用接入Key
	 */
	public String getAppKey() {
		return appKey;
	}
	/**
	 * 系统分配给应用接入Key
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	/**
	 * appKey对应的密钥值，密文传输，md5(明文)
	 */
	public String getSecretKey() {
		return secretKey;
	}
	/**
	 * appKey对应的密钥值，密文传输，md5(明文)
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	/**
	 * 签名算法
	 */
	public String getSignMethod() {
		return signMethod;
	}
	/**
	 * 签名算法
	 */
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
	/**
	 * API输入参数签名结果，具体以各API接口为准
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * API输入参数签名结果，具体以各API接口为准
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * 用户调用token获取接口，系统颁发给应用的授权信息。当接口注明：“需要授权”，则此参数必传；“不需要授权”，则此参数不需要传；“可选授权”，则此参数为可选。
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 用户调用token获取接口，系统颁发给应用的授权信息。当接口注明：“需要授权”，则此参数必传；“不需要授权”，则此参数不需要传；“可选授权”，则此参数为可选。
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8.
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * 时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8.
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 响应格式。默认为json格式。
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * 响应格式。默认为json格式。
	 */
	public void setFormat(String format) {
		this.format = format;
	}

}
