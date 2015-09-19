/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.List;

import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;

/**
 * Service接口 - 日志
 */

public interface JobService extends BaseService<Job, String>{

	boolean isExistByName(String name);

	Job getMaxSortNo();
	public Job getByName(String name);
	public List<Job> getListByName(String name);
	Job get(String id);
	
	/**
	 * 获取关键字岗位
	 * @return
	 */
	public List<Job> getKeyJobList();
	
}