/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;

import java.util.List;

import com.rzrk.entity.UserPlanLog;

/**
 * Dao接口 - 日志
 */

public interface UserPlanLogDao extends BaseDao<UserPlanLog, String> {

	/**
	 * get by user plan id
	 * @param id
	 * @return
	 * @author songkez
	 * @since  2015-6-19 
	 */
	List<UserPlanLog> getByUserPlanId(String id);

	List<UserPlanLog> getByProjectId(String id);

	/**
	 * delete by user plan id 
	 * @param id
	 * @author songkez
	 * @since  2015-7-2 
	 */
	void deleteByUserPlanId(String id);

}
