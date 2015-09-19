/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.Job;

/**
 * Dao接口 - 日志
 */

public interface JobDao extends BaseDao<Job, String> {

	boolean isExistByName(String name);

	Job getMaxSortNo();
	public Job getByName(String name);
	public List<Job> getListByName(String name);
	/**
	 * 获取关键字岗位
	 * @return
	 */
	public List<Job> getKeyJobList();
}
