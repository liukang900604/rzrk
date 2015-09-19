package com.rzrk.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rzrk.bean.Pager;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.UserPlanDao;
import com.rzrk.dao.UserPlanRequireDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.LogClass;
import com.rzrk.entity.UserPlan;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.entity.UserPlanRequire;
import com.rzrk.service.AdminService;
import com.rzrk.service.UserPlanLogService;
import com.rzrk.service.UserPlanService;
import com.rzrk.util.StrUtil;

@Service("userPlanServiceImpl")
public class UserPlanServiceImpl extends BaseServiceImpl<UserPlan, String> implements UserPlanService{
	
	@Resource(name = "userPlanDaoImpl")
	private UserPlanDao userPlanDao;
	
	@Resource(name = "userPlanDaoImpl")
	public void setBaseDao(UserPlanDao userPlanDao) {
		super.setBaseDao(userPlanDao);
	}
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "userPlanLogServiceImpl")
	private UserPlanLogService userPlanLogService;
	
	@Resource
	UserPlanRequireDao userPlanRequireDao;
	
	@Resource
	ContractDao contractDao;

	/*  @see com.rzrk.service.UserPlanService#getListByProjectId(java.lang.String)  */
	@Override
	public List<UserPlan> getListByProjectId(String id,Pager pager) {
		return userPlanDao.getListByProjectId(id,pager);
	}

	/*  @see com.rzrk.service.UserPlanService#update(com.rzrk.entity.UserPlan, com.rzrk.entity.UserPlan)  */
	@Override
	public void update(UserPlan userPlan,List<String> requireIds, UserPlan persistent, Admin admin) {
	
		Class<?> clazz = UserPlan.class;
		Method [] methods = clazz.getMethods();
		for(int i = 0; i < methods.length; i++){
			LogClass log=methods[i].getAnnotation(LogClass.class);  
			if(log != null){
				try {
					if(!StrUtil.compare(methods[i].invoke(userPlan), methods[i].invoke(persistent))){
						UserPlanLog upl = new UserPlanLog();
						upl.setOperator(admin);
						if(StringUtils.isEmpty(methods[i].invoke(persistent).toString())){
							upl.setContent(log.comments()+"   -->"+methods[i].invoke(userPlan));
						}else{
							if(StringUtils.isEmpty(methods[i].invoke(userPlan).toString())){
								upl.setContent(log.comments()+methods[i].invoke(persistent)+"-->  ");
							}else{
								upl.setContent(log.comments()+methods[i].invoke(persistent)+"-->"+methods[i].invoke(userPlan));
							}
							
						}
						
						upl.setUserPlan(persistent);
						userPlanLogService.save(upl);
					}
				} catch (Exception e) {
					
				}

			}
		}
		
		
		//记录处理人转变
		if(!persistent.getAdmin().getId().equals(userPlan.getAdmin().getId())){
					UserPlanLog upl = new UserPlanLog();
					upl.setOperator(admin);
					upl.setContent("处理人改变："+persistent.getAdmin().getName()+"-->"+adminService.get(userPlan.getAdmin().getId()).getName());
					upl.setUserPlan(persistent);
					userPlanLogService.save(upl);
		}
		
		//测试人改变
		if(persistent.getTestPerson()==null || !persistent.getTestPerson().getId().equals(userPlan.getTestPerson().getId())){
					UserPlanLog upl = new UserPlanLog();
					upl.setOperator(admin);
					upl.setContent("测试人改变："+ ((persistent.getTestPerson()==null) ? "" : persistent.getTestPerson().getName()) +"-->"+adminService.get(userPlan.getTestPerson().getId()).getName());
					upl.setUserPlan(persistent);
					userPlanLogService.save(upl);
		}
		
		
		BeanUtils.copyProperties(userPlan, persistent, new String[] {"id", "createDate", "modifyDate","userPlanUUID","creator","fileName","filePath","copyLink"});
		update(persistent);
		userPlanRequireDao.removeByUserPlan(persistent.getId());
		for(String requireId : requireIds){
			UserPlanRequire userPlanRequire = new UserPlanRequire();
			userPlanRequire.setRowId(requireId);
			userPlanRequire.setUserPlanId(persistent.getId());
			userPlanRequireDao.save(userPlanRequire);
		}
	}

	@Override
	public boolean deleteByUserPlanId(String[] ids) {
//		for(String id : ids ){
//			userPlanLogService.deleteByUserPlanId(id);
//		}
		return userPlanDao.deleteByUserPlanId(ids);
		
	}
	
	public void save(UserPlan userPlan,List<String> requireIds){
		userPlanDao.save(userPlan);
		userPlanRequireDao.removeByUserPlan(userPlan.getId());
		for(String requireId : requireIds){
			UserPlanRequire userPlanRequire = new UserPlanRequire();
			userPlanRequire.setRowId(requireId);
			userPlanRequire.setUserPlanId(userPlan.getId());
			userPlanRequireDao.save(userPlanRequire);
		}
	};

	public List<UserPlan> getRequireRelation(String rowId){
		return userPlanDao.getRequireRelation(rowId);
	}
	
	public void addPlansForRequire(String requireId,String[] userPlanIds){
		userPlanRequireDao.removeByRequire(requireId);
		for(String userPlanId : userPlanIds){
			UserPlanRequire userPlanRequire = new UserPlanRequire();
			userPlanRequire.setRowId(requireId);
			userPlanRequire.setUserPlanId(userPlanId);
			userPlanRequireDao.save(userPlanRequire);
		}
	}
	
	/**
	 * 获取项目下的计划
	 * @param projectId
	 * @return
	 */
	public List<UserPlan>  getUserPlanByProjectId(String projectId){
		String hql = "from UserPlan where project.id =:projectId";
		return this.getBaseDao().getSession().createQuery(hql).setParameter("projectId", projectId).list();
		
	}
	
}
