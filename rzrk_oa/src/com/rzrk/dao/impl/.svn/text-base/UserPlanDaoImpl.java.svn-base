/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.rzrk.bean.Pager;
import com.rzrk.dao.UserPlanDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.SystemMessage;
import com.rzrk.entity.UserPlan;

/**
 * Dao实现类 - 用户计划
 */

@Repository("userPlanDaoImpl")
public class UserPlanDaoImpl extends BaseDaoImpl<UserPlan, String> implements UserPlanDao {

	/*  @see com.rzrk.dao.UserPlanDao#getListByProjectId(java.lang.String)  */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPlan> getListByProjectId(String id,Pager pager) {
		String keyword = pager.getKeyword();
		int pageSize=pager.getPageSize();
 		int startRow=(pager.getPageNumber()-1)*pager.getPageSize();
 		String orderStr = "order by modify_date desc";
 		if(StringUtils.isNotEmpty(pager.getOrderBy())){
 			orderStr = " order by u." + pager.getOrderBy() + " " + pager.getOrder();
 		}
 		
		if (StringUtils.isNotEmpty(keyword)) {
			StringBuffer countHql = new StringBuffer("select count(*) as num from rzrk_user_plan as u where u.project_id ='"+ id + "' and u.name like '%" + keyword + "%'");
			long count = (Long) getSession().createSQLQuery(countHql.toString()).addScalar("num", Hibernate.LONG).uniqueResult();
			pager.setTotalCount((int)count);
			StringBuffer hql = new StringBuffer("select * from rzrk_user_plan as u where u.project_id ='"+ id + "' and u.name like '%" + keyword + "%'" + orderStr);
			hql.append("  limit "+startRow+","+pageSize);
			return (List<UserPlan>) getSession().createSQLQuery(hql.toString()).addEntity(UserPlan.class).list();
		}
		StringBuffer countHql = new StringBuffer("select count(*) as num from rzrk_user_plan as u where u.project_id ='" + id + "'");
		long count = (Long) getSession().createSQLQuery(countHql.toString()).addScalar("num", Hibernate.LONG).uniqueResult();
		pager.setTotalCount((int)count);
		StringBuffer hql = new StringBuffer("select * from rzrk_user_plan as u where u.project_id ='" + id + "'" + orderStr);
		hql.append("  limit "+startRow+","+pageSize);
		return (List<UserPlan>) getSession().createSQLQuery(hql.toString()).addEntity(UserPlan.class).list();		
	}

	@Override
	public boolean deleteByUserPlanId(String[] ids) {
		for (String id : ids) {
			String deleteRemark = "delete from rzrk_user_plan_remark  where userPlan ='"+id+"'";
			String deleteLog = "delete from rzrk_user_plan_log  where user_plan_id ='"+id+"'";
			String deleteUserplan = "delete from rzrk_user_plan where id ='"+id+"'";
			String deleteUserplanRequire = "delete from rzrk_user_plan_require where user_plan_id ='"+id+"'";
			getSession().createSQLQuery(deleteRemark).executeUpdate();
			getSession().createSQLQuery(deleteLog).executeUpdate();
			getSession().createSQLQuery(deleteUserplan).executeUpdate();
			getSession().createSQLQuery(deleteUserplanRequire).executeUpdate();
		}
		return true;
	}
	
	public List<UserPlan> getRequireRelation(String rowId){
		String hqlstr = "select p from UserPlan as p,UserPlanRequire as ur where p.id = ur.userPlanId and  ur.rowId = :rowId ";
		return getSession().createQuery(hqlstr).setString("rowId", rowId).list();

	}

}