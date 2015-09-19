/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.rzrk.dao.DeparmentDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;

/**
 * Dao实现类 - 日志
 */

@Repository("deparmentDaoImpl")
public class DeparmentDaoImpl extends BaseDaoImpl<Deparment, String> implements DeparmentDao {

	@Override
	public boolean isExistByName(String name) {
		String hql = "from Deparment as deparment where deparment.name = :name";
		Deparment deparment = (Deparment) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		if (deparment != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Deparment getMaxSortNo() {
		String hql = "from Deparment order by sortNo desc";
		Deparment deparment = (Deparment) getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).uniqueResult();
		if (deparment != null) {
			return deparment;
		} else {
			return null;
		}
	}
	
    public Deparment getByName(String name) {
		String hql = "from Deparment as d where d.name = :name";
		return (Deparment) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
	}
	@Override
	public List<Deparment> getListByName(String name) {
		String hql = "from Deparment as d where d.name like :name";
		return (List<Deparment>) getSession().createQuery(hql).setParameter("name", name).list();
	}


	@Override
	public Boolean checkIfHasRootDeparment(String id) {
		if(!StringUtils.isEmpty(id)){
			String hql = "from Deparment as deparment where deparment.parent = NULL and deparment.id != :id";
			Deparment deparment = (Deparment) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
			if (deparment != null) {
				return true;
			} else {
				return false;
			}
		}else{
			String hql = "from Deparment as deparment where deparment.parent = NULL";
			Deparment deparment = (Deparment) getSession().createQuery(hql).uniqueResult();
			if (deparment != null) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * 获取子部门
	 * @param deparmentSet
	 * @return
	 */
	public void  getSubDeparment(Collection<Deparment> deparmentSet,Deparment  deparment){
		if(deparment != null){//当前部门不为空
			deparmentSet.add(deparment);
			for(Deparment  dep:deparment.getChildDeparments()){
				getSubDeparment(deparmentSet,dep);
			}
		}
	}

	/*  @see com.rzrk.dao.DeparmentDao#getDeparmentByName(java.lang.String)  */
	@Override
	public Deparment getDeparmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	private void removeDeparment(Deparment deparment,Set<Deparment> departmentSet){
		
		Iterator<Deparment> ite = departmentSet.iterator();
		while(ite.hasNext()){
			Deparment  _deparment = ite.next();
			if(StringUtils.equals(_deparment.getId(), _deparment.getId())){
				ite.remove();
			}
		}
	}
    @Override
    public void delete(Deparment entity) {
        Assert.notNull(entity, "entity is required");
        
        for(Admin admin : entity.getDeparmentAdmins()){
        	removeDeparment(entity,admin.getDeparmentSet());
        }
        
        getSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(String id) {
        Assert.notNull(id, "id is required");
        Deparment entity = (Deparment) getSession().load(Deparment.class, id);
        delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(String[] ids) {
        Assert.notEmpty(ids, "ids must not be empty");
        for (String id : ids) {
        	delete(id);
        }
    }

}