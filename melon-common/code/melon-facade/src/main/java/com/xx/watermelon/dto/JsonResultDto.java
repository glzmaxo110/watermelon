package com.xx.watermelon.dto;

import java.io.Serializable;

public class JsonResultDto implements Serializable{

	private static final long serialVersionUID = 8077625764288353499L;
	private Integer errCode = 0;
	private String errMessage = "SUCCESS";
	private Object outputData = null;
	/**
	 * 错误代码 0正常 -1错误
	 * @return the errCode
	 */
	public Integer getErrCode() {
		return errCode;
	}
	/**
	 * 错误代码 0正常 -1错误
	 * @param errCode the errCode to set
	 */
	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}
	/**
	 * 错误信息，默认SUCCESS
	 * @return the errMessage
	 */
	public String getErrMessage() {
		return errMessage;
	}
	/**
	 * @param errMessage the errMessage to set
	 */
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	/**
	 * 其他数据
	 * @return the outputData
	 */
	public Object getOutputData() {
		return outputData;
	}
	/**
	 * @param outputData the outputData to set
	 */
	public void setOutputData(Object outputData) {
		this.outputData = outputData;
	}
}
