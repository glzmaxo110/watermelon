package com.zc.travel.log.dao.impl;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.log.vo.LogVo;
import com.zc.travel.msg.exception.MsgException;
import org.springframework.stereotype.Repository;

import com.zc.travel.common.core.dao.BaseDao;
import com.zc.travel.log.dao.ILogDao;
import com.zc.travel.log.mapper.LogMapper;

@Repository("logDao")
public class LogDao extends BaseDao<LogMapper> implements ILogDao {


}
