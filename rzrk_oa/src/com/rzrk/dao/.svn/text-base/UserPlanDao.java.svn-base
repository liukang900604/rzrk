/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;


import java.util.List;

import com.rzrk.bean.Pager;
import com.rzrk.entity.UserPlan;

/**
 * UserPlan接口 - 人员
 */

public interface UserPlanDao extends BaseDao<UserPlan, String> {

	/**
	 * TODO:Please add method comments
	 * @param id
	 * @return
	 * @author songkez
	 * @since  2015-6-10 
	 */
	List<UserPlan> getListByProjectId(String id,Pager pager);

	boolean deleteByUserPlanId(String[] ids);
	
	public List<UserPlan> getRequireRelation(String rowId);
}