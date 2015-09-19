package com.rzrk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rzrk.dao.UserPlanRemarkDao;
import com.rzrk.entity.SystemMessage;
import com.rzrk.entity.UserPlanRemark;

@Repository("userPlanRemarkDaoImpl")
public class UserPlanRemarkDaoImpl extends BaseDaoImpl<UserPlanRemark, String>
implements UserPlanRemarkDao {

	@Override
	public List<UserPlanRemark> getUserPlanRemark(String id) {
		StringBuffer hql =new StringBuffer("select * from rzrk_user_plan_remark where userPlan ='"+id+"'");
		List<UserPlanRemark> list =getSession().createSQLQuery(hql.toString()).addEntity(UserPlanRemark.class).list();
		return list;
	}

	@Override
	public List<UserPlanRemark> getUserPlanRemarkByProject(String id) {
		StringBuffer hql =new StringBuffer("select * from rzrk_user_plan_remark where project ='"+id+"'");
		List<UserPlanRemark> list =getSession().createSQLQuery(hql.toString()).addEntity(UserPlanRemark.class).list();
		return list;
	}
	
}
