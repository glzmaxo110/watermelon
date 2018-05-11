package com.zc.travel.log.provider;

import com.zc.travel.log.mapper.LogMapper;
import com.zc.travel.user.mapper.CrmMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;
import com.zc.travel.log.exception.LogException;
import com.zc.travel.log.service.ILogService;
import com.zc.travel.log.vo.LogVo;

@Component("logReadProvider")
public class LogReadProvider implements ILogReadProvider {

	@Autowired
	private ILogService logService;
	
	@Override
	public PageBean getLogPage(PageParam pageParam, LogVo logVo,CrmMemberMapper crmMemberMapper) throws LogException, BaseCoreException {
		return logService.getLogPage(pageParam, logVo, crmMemberMapper);
	}

	@Override
	public LogMapper getById(Long id) throws BaseCoreException {
		return logService.getById(id);
	}

}
