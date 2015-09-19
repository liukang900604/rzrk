/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.WorkDao;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Work;

/**
 * Dao实现类 - 我的工作
 */

@Repository
public class WorkDaoImpl extends BaseDaoImpl<Work, String> implements WorkDao {

	public List<Work> getByContractFieldPrimary(ContractField primaryField){
		Criteria criteria = getSession().createCriteria(Work.class);
		criteria.add(Restrictions.in("fieldSet", new Object[]{primaryField}));
		
		String sql = "select w.* from rzrk_work as w,rzrk_work_contract_record as wf where wf.work_id = w.id and wf.row_id = :fieldId";
		List<Work> work = getSession().createSQLQuery(sql).addEntity(Work.class).setString("fieldId", primaryField.getId()).list();
		
		return work;
	}


}