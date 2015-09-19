/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;









import org.springframework.stereotype.Service;

import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.JobDao;
import com.rzrk.dao.LogDao;
import com.rzrk.entity.Job;
import com.rzrk.entity.Log;
import com.rzrk.service.JobService;
import com.rzrk.service.LogService;

/**
 * Service实现类 - 日志
 */
@Service("jobServiceImpl")
public class JobServiceImpl extends BaseServiceImpl<Job, String> implements JobService {
	
	@Resource(name = "jobDaoImpl")
	private JobDao jobDao;

	@Resource(name = "jobDaoImpl")
	public void setBaseDao(JobDao jobDao) {
		super.setBaseDao(jobDao);
	}

	@Override
	public boolean isExistByName(String name) {
		return jobDao.isExistByName(name);
	}

	@Override
	public Job getMaxSortNo() {
		return jobDao.getMaxSortNo();
	}

	public Job getByName(String name){
		return jobDao.getByName(name);
	}

	public List<Job> getListByName(String name){
		return jobDao.getListByName(name);
	}
	@Override
	public Job get(String id)
	{
		return jobDao.get(id);
	}
	
	/**
	 * 获取关键字岗位
	 * @return
	 */
	public List<Job> getKeyJobList(){
		return this.jobDao.getKeyJobList();
	}
	
}