package com.rzrk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.contants.UserPlanContants;
import com.rzrk.dao.AdminUserPlanDao;
import com.rzrk.entity.AdminUserPlan;
import com.rzrk.entity.UserPlan;
import com.rzrk.service.AdminUserPlanService;





/**
 * 个人工作计划
 * @author kang.liu
 *
 */
@Transactional
@Service
public class AdminUserPlanServiceImpl extends BaseServiceImpl<AdminUserPlan, String> implements AdminUserPlanService {
     
	@Resource
	private AdminUserPlanDao typeDao;

	@Resource(name = "adminUserPlanDaoImpl")
	public void setBaseDao(AdminUserPlanDao typeDao) {
		super.setBaseDao(typeDao);
	}
	

	/**
	 * 获取个人工作计划
	 * @param param
	 * @return
	 */
	public AdminUserPlan getAdminUserPlan(Map<String,Object> param){
		String hql = "from AdminUserPlan  where followAdmin.id =:adminId and userPlan.id =:userPlanId ";
		return (AdminUserPlan) this.getBaseDao().getSession().createQuery(hql).setProperties(param).setMaxResults(1).uniqueResult();
		
	}
	
	/**
	 * 获取个人关注的工作计划
	 */
	public List<UserPlan>  getAdminUserPlanList(String adminId){
		String hql = "select new UserPlan(userPlan.id) from  AdminUserPlan where isFollow =:isFollow and  followAdmin.id =:adminId ";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("isFollow", UserPlanContants.FOLLOW).setParameter("adminId", adminId).list();
	}


}
