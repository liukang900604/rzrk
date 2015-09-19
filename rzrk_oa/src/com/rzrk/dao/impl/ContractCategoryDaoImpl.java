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
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.DeparmentDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Deparment;
import com.rzrk.vo.contract.Definition;
import com.rzrk.vo.contract.Field;

/**
 * Dao实现类 - 分类
 */

@Repository("contractCategoryDaoImpl")
public class ContractCategoryDaoImpl extends BaseDaoImpl<ContractCategory, String> implements ContractCategoryDao{
	
	@Resource
	DeparmentDao deparmentDao;
	
	 public ContractCategory find(String key,Object value) {
	        try {
				return (ContractCategory) getSession().createCriteria(ContractCategory.class)
						.add( Restrictions.eq(key,value)).setMaxResults(1).uniqueResult();
			} catch (HibernateException e) {
				return null;
			}
	}
	 
	public String getMajorField(String id){
		ContractCategory contractCategory = get(id);
//		if(contractCategory!=null){
//			String fields = contractCategory.getFields();
//			if(fields!=null){
//				return fields.split(",",2)[0];
//			}
//		}
		return contractCategory.getDefinitionObj().getPrimary().getName();
	}

	@Override
	public boolean isExistByCategoryname(String name) {
		String hql = "from ContractCategory as cc where lower(cc.name) = lower(:name)";
		ContractCategory admin = (ContractCategory) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据名称获取二级分类
	 * @param name
	 * @return
	 */
	public ContractCategory getContractCategoryByCategoryname(String name) {
		String hql = "from ContractCategory as cc where lower(cc.name) = lower(:name)";
		return  (ContractCategory) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		
	}
//	@Override
//	public boolean deleteWithCheck(String contractCategoryId) {
//		String hqlDelete = " from ContractField where contractCategoryId = :contractCategoryId";
//		List<ContractField> list = (List<ContractField>) getSession().createQuery(hqlDelete).setParameter("contractCategoryId", contractCategoryId).list();
//		if (list.size()!=0) {
//			return false;
//		}else {
//			return true;
//		}
//		
//	}

	public List<Field> getPermissionField(ContractCategory  contractCategory, Admin admin){
		Collection<String> myDepList = new ArrayList<String>();
		Collection<String> myDepWithSubList = new ArrayList<String>();
		for(Deparment deparment : admin.getDeparmentSet()){
			myDepList.add(deparment.getId());
			List<Deparment> depList = new ArrayList<Deparment>();
			deparmentDao.getSubDeparment(depList, deparment);
			myDepWithSubList.addAll(CollectionUtils.collect(depList, new Transformer() {
				@Override
				public Object transform(Object arg0) {
					Deparment dep = (Deparment)arg0;
					return dep.getId();
				}
			}));
		}
		List<Field> fieldList = new ArrayList<Field>();
		Definition definition = contractCategory.getDefinitionObj();
		for(int i=0;i<definition.size();i++){
			Field fieldDef = definition.get(i);
			Collection<String> myList = null;
			if(fieldDef.isSuperiorView()){
				myList = myDepWithSubList;
			}else{
				myList = myDepList;
			}
			if(
					(fieldDef.getDepartmentIds().isEmpty() && fieldDef.getAdminIds().isEmpty()) //没有限制部门和人
				||	(CollectionUtils.containsAny(myList, fieldDef.getDepartmentIds())) //部门匹配
				|| (fieldDef.getAdminIds().contains(admin.getId()))	//人员匹配
					
					){
				fieldList.add(fieldDef);
			}
		}
		return fieldList;
	}
}