package com.zc.travel.log.exception;

import com.zc.travel.common.exceptions.BaseCoreException;

public class LogException extends BaseCoreException {
	
	private static final long serialVersionUID = -6191971724254055015L;

	public LogException(String code) {
		super(code);
	}

}
