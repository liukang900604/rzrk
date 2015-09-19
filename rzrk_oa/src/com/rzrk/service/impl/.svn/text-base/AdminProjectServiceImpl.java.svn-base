package com.rzrk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.contants.UserPlanContants;
import com.rzrk.dao.AdminProjectDao;
import com.rzrk.entity.AdminProject;
import com.rzrk.entity.Project;
import com.rzrk.service.AdminProjectService;





/**
 * 个人项目计划
 * @author kang.liu
 *
 */
@Transactional
@Service
public class AdminProjectServiceImpl extends BaseServiceImpl<AdminProject, String> implements AdminProjectService {
     
	@Resource
	private AdminProjectDao typeDao;

	@Resource(name = "adminProjectDaoImpl")
	public void setBaseDao(AdminProjectDao typeDao) {
		super.setBaseDao(typeDao);
	}
	


	/**
	 * 获取个人项目
	 * @param param
	 * @return
	 */
	public AdminProject getAdminProject(Map<String,Object> param){
		String hql = "from AdminProject  where followAdmin.id =:adminId and project.id =:projectId ";
		return (AdminProject) this.getBaseDao().getSession().createQuery(hql).setProperties(param).setMaxResults(1).uniqueResult();
		
	}
	
	
	/**
	 * 获取个人关注的项目
	 */
	public List<Project>  getAdminProjectList(String adminId){
		String hql = "select new Project(project.id) from  AdminProject where isFollow =:isFollow and  followAdmin.id =:adminId ";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("isFollow", UserPlanContants.FOLLOW).setParameter("adminId", adminId).list();
	}

}
