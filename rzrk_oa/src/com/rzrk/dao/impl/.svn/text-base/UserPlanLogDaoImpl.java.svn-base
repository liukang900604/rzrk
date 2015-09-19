/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.rzrk.dao.UserPlanLogDao;
import com.rzrk.entity.UserPlanLog;

/**
 * Dao实现类 - 日志
 */

@Repository("userPlanLogDaoImpl")
public class UserPlanLogDaoImpl extends BaseDaoImpl<UserPlanLog, String> implements UserPlanLogDao {


	@Override
	public List<UserPlanLog> getByUserPlanId(String id) {
		String hql = "from UserPlanLog as upl where upl.userPlan.id = :id order by createDate desc";
		return getSession().createQuery(hql).setParameter("id", id).list();
	}

	@Override
	public List<UserPlanLog> getByProjectId(String id) {
		String hql = "from UserPlanLog as upl where upl.project.id = :id order by createDate desc";
		return getSession().createQuery(hql).setParameter("id", id).list();
	}

	/*  @see com.rzrk.dao.UserPlanLogDao#deleteByUserPlanId(java.lang.String)  */
	@Override
	public void deleteByUserPlanId(String id) {
		String hql = "delete from UserPlanLog as upl where upl.userPlan.id = :id";
	    getSession().createQuery(hql).setParameter("id", id);		
	}
}

