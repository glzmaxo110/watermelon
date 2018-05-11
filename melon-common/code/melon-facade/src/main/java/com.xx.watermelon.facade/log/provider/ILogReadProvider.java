package com.zc.travel.log.provider;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;
import com.zc.travel.log.exception.LogException;
import com.zc.travel.log.mapper.LogMapper;
import com.zc.travel.log.vo.LogVo;
import com.zc.travel.user.mapper.CrmMemberMapper;

public interface ILogReadProvider {
	
	/**
	 * 分页获取日志信息
	 * @param pageParam	分页信息
	 * @param params	查询条件
	 * @return
	 * @throws BaseCoreException
	 */
	PageBean getLogPage(PageParam pageParam, LogVo logVo,CrmMemberMapper crmMemberMapper) throws LogException, BaseCoreException;
	 LogMapper getById(Long id) throws BaseCoreException;
}
