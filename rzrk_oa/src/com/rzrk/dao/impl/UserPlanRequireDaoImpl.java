/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.rzrk.bean.Pager;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.UserPlanRequireDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.UserPlanRequire;
import com.rzrk.vo.DeparmentAdminVo;

/**
 * Dao实现类 - 人员
 */

@Repository("userPlanRequireDaoImpl")
public class UserPlanRequireDaoImpl extends BaseDaoImpl<UserPlanRequire, String> implements UserPlanRequireDao {
	
	public void removeByUserPlan(String userPlanId){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserPlanRequire.class);
		detachedCriteria.add(Restrictions.eq("userPlanId", userPlanId));
		 List<UserPlanRequire> userPlanRequireList = find(detachedCriteria);
		for(UserPlanRequire userPlanRequire : userPlanRequireList){
			delete(userPlanRequire);
		}
	}

	public void removeByRequire(String rowId){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserPlanRequire.class);
		detachedCriteria.add(Restrictions.eq("rowId", rowId));
		 List<UserPlanRequire> userPlanRequireList = find(detachedCriteria);
		for(UserPlanRequire userPlanRequire : userPlanRequireList){
			delete(userPlanRequire);
		}
	}

}