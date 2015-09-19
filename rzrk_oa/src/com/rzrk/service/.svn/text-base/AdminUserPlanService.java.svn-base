/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;
import java.util.List;
import java.util.Map;

import com.rzrk.contants.UserPlanContants;
import com.rzrk.entity.AdminUserPlan;
import com.rzrk.entity.UserPlan;


/**
 * 用户计划接口
 * @author kang.liu
 */

public interface AdminUserPlanService extends BaseService<AdminUserPlan, String> {
	
	/**
	 * 获取个人工作计划
	 * @param param
	 * @return
	 */
	public AdminUserPlan getAdminUserPlan(Map<String,Object> param);
	
	
	/**
	 * 获取个人关注的工作计划
	 */
	public List<UserPlan>  getAdminUserPlanList(String adminId);
	
}