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

import com.rzrk.dao.RootContractCategoryDao;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;

/**
 * Dao实现类 - 分类
 */

@Repository("rootContractCategoryDaoImpl")
public class RootContractCategoryDaoImpl extends BaseDaoImpl<RootContractCategory, String> implements RootContractCategoryDao{

	@Override
	public boolean isExistByName(String name) {
		String hql = "from RootContractCategory as rcc where rcc.name = :name";
		RootContractCategory rcc = (RootContractCategory) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		if (rcc != null) {
			return true;
		} else {
			return false;
		}
	}

	/*  @see com.rzrk.dao.RootContractCategoryDao#getByType(int)  */
	@SuppressWarnings("unchecked")
	@Override
	public List<RootContractCategory> getByType(ROOT_CATEGORY_TYPE type) {
		String hql = "from RootContractCategory as rcc where rcc.rootCategoryType = :type";
		return getSession().createQuery(hql).setParameter("type", type).list();
	}



}