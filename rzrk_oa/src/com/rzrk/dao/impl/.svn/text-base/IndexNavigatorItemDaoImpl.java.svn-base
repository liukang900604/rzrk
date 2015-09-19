/**
 * Project Name: OA System
 * Confidential and Proprietary                                                                
 * Copyright (C) 2015 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.IndexNavigatorItemDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.IndexNavigatorItem;

/**                                                                                                                                    
 * Purpose:   IndexNavigatorItemDaoImpl                                                                 
 * @version $Id$                                                          
 * @author songkez
 * @since 2015-7-10 
 */
@Repository("indexNavigatorItemDaoImpl")
public class IndexNavigatorItemDaoImpl extends BaseDaoImpl<IndexNavigatorItem, String> implements IndexNavigatorItemDao{

	@Override
	public void deleteAll() {
		String hql = "delete from IndexNavigatorItem";
		getSession().createQuery(hql).executeUpdate();
	}

	/*  @see com.rzrk.dao.IndexNavigatorItemDao#getAllUserList(com.rzrk.entity.Admin)  */
	@Override
	public List<IndexNavigatorItem> getAllUserList(Admin loginAdmin) {
		String hql = "from IndexNavigatorItem as item where item.admin.id = :id";
		return (List<IndexNavigatorItem>) getSession().createQuery(hql).setParameter("id", loginAdmin.getId()).list();
	}

	/*  @see com.rzrk.dao.IndexNavigatorItemDao#deleteItemByUrl(java.lang.String, com.rzrk.entity.Admin)  */
	@Override
	public void deleteItemByUrl(String temp, Admin loginAdmin) {
		String hql = "delete from IndexNavigatorItem as item where item.admin.id = :id and item.url = :url";
		getSession().createQuery(hql).setParameter("id", loginAdmin.getId()).setParameter("url", temp).executeUpdate();
		
	}
	
	/**
	 * 删除用户的关注
	 * @param temp
	 * @param loginAdmin
	 */
	public void deleteItemByAdmin(Admin loginAdmin) {
		String hql = "delete from IndexNavigatorItem as item where item.admin.id =:id";
		getSession().createQuery(hql).setParameter("id", loginAdmin.getId()).executeUpdate();
	}

	/*  @see com.rzrk.dao.IndexNavigatorItemDao#getOneByUrl(java.lang.String, com.rzrk.entity.Admin)  */
	@Override
	public IndexNavigatorItem getOneByUrl(String string, Admin loginAdmin) {
		String hql = "from IndexNavigatorItem as item where item.admin.id = :id and item.url = :url";
		try {
			return (IndexNavigatorItem) getSession().createQuery(hql).setParameter("id", loginAdmin.getId()).setParameter("url", string).uniqueResult();
		} catch (HibernateException e) {
			return null;
		}
	}

}
