package com.zc.travel.log.provider;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.log.mapper.LogMapper;
import com.zc.travel.msg.exception.MsgException;

public interface ILogOptProvider {
	/**
	 * 日志数据保存
	 * @param systemCode	业务系统编码，取SystemCode枚举值
	 * @param userId		操作用户ID
	 * @param ipAddr		操作IP地址
	 * @param keyWords		关键词
	 * @param content		日志内容
	 * @param logOptType	日志操作类型，取LogTable枚举值
	 * @param logOptStatus	日志操作状态，取LogTable枚举值
	 * @param logOptLevel	日志操作级别，取LogTable枚举值
	 * @param logType		日志类型
	 */
	void writeLog(Integer systemCode, Long userId, String ipAddr, String keyWords, String content, Integer logOptType, Integer logOptStatus, Integer logOptLevel, Integer logType);


	void getById(Long id) throws MsgException, BaseCoreException;
}
