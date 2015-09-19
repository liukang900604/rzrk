/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.rzrk.dao.LogDao;
import com.rzrk.entity.Log;
import com.rzrk.service.LogService;

/**
 * Service实现类 - 日志
 */

@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	@Resource(name = "logDaoImpl")
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}