/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import com.rzrk.entity.JobLevel;

/**
 * Service接口 - 日志
 */

public interface JobLevelService extends BaseService<JobLevel, String>{

	boolean isExistByName(String name);

	JobLevel getMaxSortNo();
	
}