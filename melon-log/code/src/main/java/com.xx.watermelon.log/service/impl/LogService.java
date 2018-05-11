package com.zc.travel.log.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zc.travel.consts.CommonConstants;
import com.zc.travel.msg.exception.MsgException;
import com.zc.travel.product.exception.ProductException;
import com.zc.travel.user.mapper.CrmMemberMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;
import com.zc.travel.enums.LogOptLevel;
import com.zc.travel.enums.LogOptStatus;
import com.zc.travel.enums.LogOptType;
import com.zc.travel.log.dao.ILogDao;
import com.zc.travel.log.exception.LogException;
import com.zc.travel.log.mapper.LogMapper;
import com.zc.travel.log.service.ILogService;
import com.zc.travel.log.vo.LogVo;

@Service("logService")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class LogService implements ILogService {
	
	private static final Logger LOGGER = Logger.getLogger(LogService.class);
	@Autowired
	private ILogDao logDao;
	
	@Override
	public void save(Long userId, String ipAddr, String keyWords,
			String content, Integer optType, Integer optStatus, Integer optLevel, Integer systemCode, Integer logType) {
		try {
			if (null == userId || StringUtils.isBlank(ipAddr)
					|| StringUtils.isBlank(keyWords) || StringUtils.isBlank(content)
					|| null == systemCode)
				return;
			
			LogMapper log = new LogMapper();
			log.setOptUserId(userId);
			log.setCreateTime(new Date());
			log.setOptType(optType);
			log.setOptStatus(optStatus);
			log.setOptLevel(optLevel);
			log.setIp(ipAddr);
			log.setOptKeywords(keyWords);
			log.setContent(content);
			log.setSystemCode(systemCode);
			log.setLogStatus(1);
			log.setLogType(logType);
			logDao.insert(log);
		} catch (BaseCoreException ex) {
			//数据库日志记录异常，则记录到文件中
			this.saveToFile(LOGGER, userId, ipAddr, keyWords, content, LogOptType.getByIndex(optType), LogOptStatus.FAILURE, LogOptLevel.ERROR);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void saveToFile(Logger logger, Long userId, String ipAddr, String keyWords, String content, LogOptType optType, LogOptStatus status, LogOptLevel level) {
		StringBuilder loggerDes = new StringBuilder();
		loggerDes
			.append("\n****[userId]:	[").append(userId).append("],")
			.append("\n****[optStatus]:	[").append(LogOptStatus.SUCCESS == status ? "success" : "failed").append("],")
			.append("\n****[keyWords]:	[").append(keyWords).append("],")
			.append("\n****[content]:	[").append(content).append("]");
		if (LogOptLevel.INFO == level) {
			logger.info(loggerDes.toString());
		} else if (LogOptLevel.DEBUG == level) {
			logger.debug(loggerDes.toString());
		} else if (LogOptLevel.WARN == level) {
			logger.warn(loggerDes.toString());
		} else if (LogOptLevel.ERROR == level) {
			logger.error(loggerDes.toString());
		}
	}

	@Override
	public PageBean getLogPage(PageParam pageParam, LogVo logVo,CrmMemberMapper crmMemberMapper) throws LogException, BaseCoreException {
		Map<String, Object> params = new HashMap<String, Object>();

		if (null != logVo) {
			if (null != logVo.getOptLevel())
				params.put("optLevel", logVo.getOptLevel());
			if (null != logVo.getOptStatus())
				params.put("optStatus", logVo.getOptStatus());
			if (null !=crmMemberMapper.getMemberName())
				params.put("memberName",crmMemberMapper.getMemberName());
			if (null != logVo.getOptType())
				params.put("optType", logVo.getOptType());
			if (StringUtils.isNotBlank(logVo.getOptKeywords()))
				params.put("optKeywords", logVo.getOptKeywords());
			if (null != logVo.getSystemCode())
				params.put("systemCode", logVo.getSystemCode());
			if (null != logVo.getLogStatus())
				params.put("logStatus", logVo.getLogStatus());
			if (null != logVo.getLogType())
				params.put("logType", logVo.getLogType());
			if (null != logVo.getStartTime())
				params.put("startTime",logVo.getStartTime());
			if (null != logVo.getEndTime())
				params.put("endTime",logVo.getEndTime());
			if (null != logVo.getContent())
				params.put("content",logVo.getContent());
		}
		return logDao.listPage(pageParam, params);
	}

	@Override
	@Transactional(readOnly = true)
	public LogMapper getById(Long id)  throws MsgException, BaseCoreException {

		return logDao.getById(id);
	}
}
