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

import com.rzrk.dao.UserPlanDao;
import com.rzrk.dao.UserPlanLogDao;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.service.UserPlanLogService;

/**
 * Service实现类 - 日志
 */

@Service("userPlanLogServiceImpl")
public class UserPlanLogServiceImpl extends BaseServiceImpl<UserPlanLog, String> implements UserPlanLogService {
	
	@Resource(name = "userPlanLogDaoImpl")
	private UserPlanLogDao userPlanLogDao;

	@Resource(name = "userPlanLogDaoImpl")
	public void setBaseDao(UserPlanLogDao userPlanLogDao) {
		super.setBaseDao(userPlanLogDao);
	}

	/*  @see com.rzrk.service.UserPlanLogService#getByUserPlanId(java.lang.String)  */
	@Override
	public List<UserPlanLog> getByUserPlanId(String id) {
		return userPlanLogDao.getByUserPlanId(id);
	}

	@Override
	public List<UserPlanLog> getByProjectId(String id) {
		 return userPlanLogDao.getByProjectId(id);
	}

	/*  @see com.rzrk.service.UserPlanLogService#deleteByUserPlanId(java.lang.String)  */
	@Override
	public void deleteByUserPlanId(String id) {
		userPlanLogDao.deleteByUserPlanId(id);		
	}

}