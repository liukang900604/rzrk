/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.rzrk.bean.Pager;
import com.rzrk.dao.SystemMessageDao;
import com.rzrk.entity.SystemMessage;

/**
 * Dao实现类 - 日志
 */

@Repository("systemMessageDaoImpl")
public class SystemMessageDaoImpl extends BaseDaoImpl<SystemMessage, String>
		implements SystemMessageDao {
	@Override
	public boolean isExistByName(String title) {
		String hql = "from SystemMessage as systemmessage where systemmessage.tile = :title";
		SystemMessage systemMessage = (SystemMessage) getSession()
				.createQuery(hql).setParameter("title", title).uniqueResult();
		if (systemMessage != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getUnredCount(String id) {
		StringBuffer hql =new StringBuffer( "select count(1) as num from rzrk_system_message a, rzrk_system_message_admin b ");
		hql.append("  WHERE a.id=b.system_message_id and a.red_type = 0 and b.to_messag_admin_id ='"+id+"'");
		long count = (Long) getSession().createSQLQuery(hql.toString()).addScalar("num", Hibernate.LONG).uniqueResult();
		return (int) count;
		
	}

	@Override
	public List<SystemMessage> getAdminMessage(String id, Pager pager) {
		final int pageSize=pager.getPageSize();
		final int startRow=(pager.getPageNumber()-1)*pager.getPageSize();
		StringBuffer countHql =new StringBuffer( "select count(1) as num from rzrk_system_message a, rzrk_system_message_admin b ");
		countHql.append("  WHERE a.id=b.system_message_id and b.to_messag_admin_id ='"+id+"'");
		long count = (Long) getSession().createSQLQuery(countHql.toString()).addScalar("num", Hibernate.LONG).uniqueResult();
		
        pager.setTotalCount((int)count);
		StringBuffer hql =new StringBuffer("select * from rzrk_system_message a, rzrk_system_message_admin b ");
		hql.append("  WHERE a.id=b.system_message_id  AND b.to_messag_admin_id ='"+id+"'  order by a.create_time desc");
		hql.append("  limit "+startRow+","+pageSize);
		
		List<SystemMessage> list =getSession().createSQLQuery(hql.toString()).addEntity(SystemMessage.class).list();
		if (list!=null) {
			return list;		
		}else{
			return new ArrayList<SystemMessage>();
		}	
	}

	@Override
	public List<SystemMessage> getMessage(String id, Pager pager) {
		final int pageSize=pager.getPageSize();
 		final int startRow=(pager.getPageNumber()-1)*pager.getPageSize();
		StringBuffer countHql =new StringBuffer("select count(1) as num from rzrk_system_message a WHERE a.is_delete is null and a.adminId ='"+id+"'");
		long count = (Long) getSession().createSQLQuery(countHql.toString()).addScalar("num", Hibernate.LONG).uniqueResult();
		
         pager.setTotalCount((int)count);
         
        
		StringBuffer hql =new StringBuffer( "select * from rzrk_system_message a WHERE a.is_delete is null and a.adminId ='"+id+"' order by a.create_time desc");
		hql.append("  limit "+startRow+","+pageSize);
		List<SystemMessage> list =getSession().createSQLQuery(hql.toString()).addEntity(SystemMessage.class).list();
		if (list!=null) {
			return list;		
		}else{
			return new ArrayList<SystemMessage>();
		}	
	}

}