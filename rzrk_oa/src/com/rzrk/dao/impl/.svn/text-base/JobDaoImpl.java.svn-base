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

import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.JobDao;
import com.rzrk.dao.LogDao;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Log;

/**
 * Dao实现类 - 日志
 */

@Repository("jobDaoImpl")
public class JobDaoImpl extends BaseDaoImpl<Job, String> implements JobDao {

	@Override
	public boolean isExistByName(String name) {
		String hql = "from Job as job where job.jobName = :name";
		Job job = (Job) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		if (job != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Job getMaxSortNo() {
		String hql = "from Job order by sortNo desc";
		Job job = (Job) getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).uniqueResult();
		if (job != null) {
			return job;
		} else {
			return null;
		}
	}
	public Job getByName(String name){
		String hql = "from Job as job where job.jobName = :name";
		Job job = (Job) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		return job;
	}

	public List<Job> getListByName(String name){
		String hql = "from Job as job where job.jobName like :name";
		return (List<Job>) getSession().createQuery(hql).setParameter("name", name).list();
	}

	/**
	 * 获取关键字岗位
	 * @return
	 */
	public List<Job> getKeyJobList(){
		String hql = "from Job where isKeyName =:keyName";
		return getSession().createQuery(hql).setParameter("keyName", WorkFlowContants.KEY_JOB).list();
	}
	
	
}