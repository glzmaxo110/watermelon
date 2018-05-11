/*
package com.zc.travel.log.provider;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.log.service.ILogService;
import com.zc.travel.msg.exception.MsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("logOptProvider")
public class LogOptProvider implements ILogOptProvider {

	@Autowired
	private ILogService logService;



	@Override
	public void writeLog(Integer systemCode, Long userId, String ipAddr,
			String keyWords, String content, Integer logOptType, Integer logOptStatus, Integer logOptLevel, Integer logType) {
		logService.save(userId, ipAddr, keyWords, content, logOptType, logOptStatus, logOptLevel, systemCode, logType);
	}

	@Override
	public void getById(Long id) throws MsgException, BaseCoreException {
		logService.getById(id);
	}


}
*/
