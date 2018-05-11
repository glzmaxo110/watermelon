package com.zc.travel.log.service;

import com.zc.travel.log.mapper.LogMapper;
import com.zc.travel.msg.exception.MsgException;
import com.zc.travel.user.mapper.CrmMemberMapper;
import org.apache.log4j.Logger;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;
import com.zc.travel.enums.LogOptLevel;
import com.zc.travel.enums.LogOptStatus;
import com.zc.travel.enums.LogOptType;
import com.zc.travel.log.exception.LogException;
import com.zc.travel.log.vo.LogVo;

public interface ILogService {
	
	/**
	 * 系统日志数据保存
	 * @param userId		操作用户ID
	 * @param ipAddr		操作IP地址
	 * @param keyWords		关键词
	 * @param content		日志内容
	 * @param optType		日志操作类型
	 * @param optStatus		日志操作状态
	 * @param optLevel		日志操作级别
	 * @param systemCode	业务系统
	 * @param logType		日志类型
	 */
	void save(Long userId, String ipAddr, String keyWords, String content, Integer optType, Integer optStatus, Integer optLevel, Integer systemCode, Integer logType);
	
	/**
	 * 日志数据保存到文件
	 * @param logger	日志文件
	 * @param userId	操作用户ID
	 * @param ipAddr	操作IP地址
	 * @param keyWords	关键词
	 * @param content	日志内容
	 * @param optType	日志操作类型
	 * @param status	日志操作状态
	 * @param level		日志操作级别
	 */
	void saveToFile(Logger logger, Long userId, String ipAddr, String keyWords, String content, LogOptType optType, LogOptStatus status, LogOptLevel level);

	/**
	 * 分页获取日志信息
	 * @param params	查询条件
	 * @param pageParam    分页信息
	 * @param crmMemberMapper
	 * @return
	 * @throws BaseCoreException
	 */
	PageBean getLogPage(PageParam pageParam, LogVo logVo, CrmMemberMapper crmMemberMapper) throws LogException, BaseCoreException;
	LogMapper getById(Long id)throws MsgException,BaseCoreException;
	
}
