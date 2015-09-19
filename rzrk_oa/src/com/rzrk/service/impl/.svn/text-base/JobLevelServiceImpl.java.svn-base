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

import com.rzrk.dao.JobDao;
import com.rzrk.dao.JobLevelDao;
import com.rzrk.dao.LogDao;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.JobLevel;
import com.rzrk.entity.Log;
import com.rzrk.service.JobLevelService;
import com.rzrk.service.JobService;
import com.rzrk.service.LogService;

/**
 * Service实现类 - 日志
 */

@Service("jobLevelServiceImpl")
public class JobLevelServiceImpl extends BaseServiceImpl<JobLevel, String> implements JobLevelService {
	
	@Resource(name = "jobLevelDaoImpl")
    private JobLevelDao jobLevelDao;

	@Resource(name = "jobLevelDaoImpl")
	public void setBaseDao(JobLevelDao jobLevelDao) {
		super.setBaseDao(jobLevelDao);
	}

	@Override
	public boolean isExistByName(String name) {
		return jobLevelDao.isExistByName(name);
	}

	@Override
	public JobLevel getMaxSortNo() {
		return jobLevelDao.getMaxSortNo();
	}

}