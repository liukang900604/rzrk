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

import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractCategoryLogDao;
import com.rzrk.dao.UserPlanLogDao;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractCategoryLog;
import com.rzrk.entity.UserPlanLog;

/**
 * Dao实现类 - 日志
 */

@Repository("contractCategoryLogDaoImpl")
public class ContractCategoryLogDaoImpl extends BaseDaoImpl<ContractCategoryLog, String> implements ContractCategoryLogDao {



	@Override
	public List<ContractCategoryLog> getByContractCategoryId(String id) {
		String hql = "from ContractCategoryLog as cl where cl.contractCategory.id = :id order by createDate desc";
		return getSession().createQuery(hql).setParameter("id", id).list();
	}

}