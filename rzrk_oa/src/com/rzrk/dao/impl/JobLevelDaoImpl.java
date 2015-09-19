/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import org.springframework.stereotype.Repository;

import com.rzrk.dao.JobLevelDao;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.JobLevel;

/**
 * Dao实现类 - 日志
 */

@Repository("jobLevelDaoImpl")
public class JobLevelDaoImpl extends BaseDaoImpl<JobLevel, String> implements JobLevelDao {

	@Override
	public boolean isExistByName(String name) {
		String hql = "from JobLevel as jobLevel where jobLevel.name = :name";
		JobLevel jobLevel = (JobLevel) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		if (jobLevel != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JobLevel getMaxSortNo() {
		String hql = "from JobLevel order by sortNo desc";
		JobLevel deparment = (JobLevel) getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).uniqueResult();
		if (deparment != null) {
			return deparment;
		} else {
			return null;
		}
	}

}