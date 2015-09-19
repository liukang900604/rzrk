/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.List;

import com.rzrk.entity.ContractCategoryLog;
import com.rzrk.entity.UserPlanLog;

/**
 * Service接口 - 日志
 */

public interface ContractCategoryLogService extends BaseService<ContractCategoryLog, String>{

	/**
	 * get by user plan id
	 * @param id
	 * @return
	 * @author songkez
	 * @since  2015-6-19 
	 */
	List<ContractCategoryLog> getByContractCategoryId(String id);
	
}