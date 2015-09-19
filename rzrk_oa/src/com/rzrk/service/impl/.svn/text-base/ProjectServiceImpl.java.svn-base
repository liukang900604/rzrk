package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rzrk.bean.Pager;
import com.rzrk.contants.UserPlanContants;
import com.rzrk.dao.ProjectDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.AdminProject;
import com.rzrk.entity.AdminUserPlan;
import com.rzrk.entity.Project;
import com.rzrk.entity.Role;
import com.rzrk.entity.UserPlan;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.AdminProjectService;
import com.rzrk.service.AdminService;
import com.rzrk.service.AdminUserPlanService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.RoleService;
import com.rzrk.service.UserPlanLogService;
import com.rzrk.service.UserPlanService;

@Service("projectServiceImpl")
public class ProjectServiceImpl extends BaseServiceImpl<Project, String> implements ProjectService{
	
	@Resource(name = "projectDaoImpl")
	private ProjectDao projectDao;
	
	@Resource(name = "projectDaoImpl")
	public void setBaseDao(ProjectDao projectDao) {
		super.setBaseDao(projectDao);
	}
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "userPlanLogServiceImpl")
	private UserPlanLogService userPlanLogService;
	
	@Resource(name = "deparmentServiceImpl")
	private DeparmentService deparmentService;
	
	@Resource
	private RoleService roleService;	
	@Resource
	private  AdminProjectService adminProjectService;
	
	@Resource
	private  UserPlanService   userPlanService;
	
	@Resource
	private AdminUserPlanService  adminUserPlanService;
	
	@Override
	public void update(Project project, Project persistent, Admin admin,List<Admin> adminList) {
		
		if(!persistent.getProjectType().equals(project.getProjectType())){
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent(persistent.getProjectType()+"-->"+project.getProjectType());
			upl.setProject(persistent);
			userPlanLogService.save(upl);
		}
		

		//开始时间
		if(!persistent.getBeginTime().equals(project.getBeginTime())){
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("开始时间修改: "+persistent.getBeginTime()+"-->"+project.getBeginTime());
			upl.setProject(persistent);
			userPlanLogService.save(upl);
		}
		
		//结束时间
		if(!persistent.getEndTime().equals(project.getEndTime())){
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("结束时间修改: "+persistent.getEndTime()+"-->"+project.getEndTime());
			upl.setProject(persistent);
			userPlanLogService.save(upl);
		}
		//进度修改
		if(persistent.getProgress() != project.getProgress()){
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("进度修改: "+persistent.getProgress()+"-->"+project.getProgress());
			upl.setProject(persistent);
			userPlanLogService.save(upl);
		}
		
		//记录项目状态变化
		if(!persistent.getStatus().equals(project.getStatus())){
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("项目状态修改:"+persistent.getStatus()+"-->"+project.getStatus());
			upl.setProject(persistent);
			userPlanLogService.save(upl);
		}
		//记录项目负责人
		if(!persistent.getResponsibor().getId().equals(project.getResponsibor().getId())){
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("负责人修改:"+persistent.getResponsibor().getName()+"-->"+adminService.get(project.getResponsibor().getId()).getName());
			upl.setProject(persistent);
			userPlanLogService.save(upl);
		}
		
		//记录项目部门
		if(!persistent.getDeparment().getId().equals(project.getDeparment().getId())){
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("项目部门修改:"+persistent.getDeparment().getName()+"-->"+deparmentService.get(project.getDeparment().getId()).getName());
			upl.setProject(persistent);
			userPlanLogService.save(upl);
		}
		
		Set<Admin> persistentSet = persistent.getProjectMember();
		Set<Admin> temp = new HashSet<Admin>(persistentSet);
		Set<Admin> projectSet = project.getProjectMember();
		StringBuffer persistentBuff = new StringBuffer();
		StringBuffer projectBuff = new StringBuffer();
		if (persistentSet.size()!=projectSet.size()&&projectSet.size()!=0) {
			for (Admin admin2 : persistentSet) {
				persistentBuff.append(admin2.getName()+"、");
			}
			for (Admin admin2 : projectSet) {
				projectBuff.append(adminService.get(admin2.getId()).getName()+"、");
			}
			if(StringUtils.isNotEmpty(persistentBuff.toString())){
				persistentBuff.replace(persistentBuff.length()-1, persistentBuff.length(), "");
			}
		    if(StringUtils.isNotEmpty(projectBuff.toString())){
		    	projectBuff.replace(projectBuff.length()-1, projectBuff.length(), ".");
		    }
		
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("项目成员修改："+persistentBuff+"-->"+projectBuff);
			upl.setProject(persistent);
			userPlanLogService.save(upl);			
		}else if (persistentSet.removeAll(projectSet)) {
			if (persistentSet.size()!=0) {
				for (Admin admin2 : temp) {
					persistentBuff.append(admin2.getName()+"、");
				}
				for (Admin admin2 : projectSet) {
					projectBuff.append(adminService.get(admin2.getId()).getName()+"、");
				}
				if(StringUtils.isNotEmpty(persistentBuff.toString())){
					persistentBuff.replace(persistentBuff.length()-1, persistentBuff.length(), "");
				}
				if(StringUtils.isNotEmpty(projectBuff.toString())){
					projectBuff.replace(projectBuff.length()-1, projectBuff.length(), ".");
				}
				
				UserPlanLog upl = new UserPlanLog();
				upl.setOperator(admin);
				upl.setContent("项目成员修改："+persistentBuff+"-->"+projectBuff);
				upl.setProject(persistent);
				userPlanLogService.save(upl);	
			}
		}
		else {
			for (Admin admin2 : persistentSet) {
				persistentBuff.append(admin2.getName()+"、");
			}
			for (Admin admin2 : projectSet) {
				projectBuff.append(adminService.get(admin2.getId()).getName()+"、");
			}
			if(StringUtils.isNotEmpty(persistentBuff.toString())){
				persistentBuff.replace(persistentBuff.length()-1, persistentBuff.length(), "");
			}
		
			if(StringUtils.isNotEmpty(projectBuff.toString())){
				projectBuff.replace(projectBuff.length()-1, projectBuff.length(), ".");
			}
			
			UserPlanLog upl = new UserPlanLog();
			upl.setOperator(admin);
			upl.setContent("项目成员修改："+persistentBuff+"-->"+projectBuff);
			upl.setProject(persistent);
			userPlanLogService.save(upl);	
		}
		
		
		BeanUtils.copyProperties(project, persistent, new String[] {"id", "createDate", "modifyDate", "rootContractCategory","creator"});
		update(persistent);
		
	}

	@Override
	public void deleteByProjectId(String[] ids) {
		projectDao.deleteByProjectId(ids);
	}
	
	@Override
	public String save(Project project){
		projectDao.save(project);
		if((project.getRootContractCategory()!=null && StringUtils.isNotEmpty(project.getRootContractCategory().getId()))){
			Role role = roleService.getSuperRole();
			List<String> tempPCList = role.getPcList();
			tempPCList.add(project.getRootContractCategory().getId());
			tempPCList.add(project.getId());
			role.setPcList(new ArrayList<String>(new TreeSet<String>(tempPCList)));
			roleService.update(role);
		}
		projectDao.save(project);
		UserPlanLog upl = new UserPlanLog();
		upl.setOperator(project.getCreator());
		upl.setContent("创建项目");
		upl.setProject(project);
		userPlanLogService.save(upl);
		return null;
	};

	public void  findPager(Pager pager,Map<String,Object> queryMap){
		projectDao.findPager(pager, queryMap);
	}
	
	
	/**
	 * 关注我的项目关注
	 * @return
	 */
	public String projectFollow(Admin login,String id,Project project){
		if(StringUtils.isEmpty(id)){
			throw new PersonalException("项目信息不存在!");		
		}
		project = get(id);
		if(project == null){
			throw new PersonalException("项目信息不存在!");		
		}
	    List<UserPlan>  userPlanList =	userPlanService.getUserPlanByProjectId(project.getId());
		Map<String,Object>  param = new HashMap<String,Object>();
		param.put("adminId", login.getId());
		param.put("projectId", project.getId());
		
		AdminProject  adminProject =adminProjectService.getAdminProject(param);
	
		try{
			
			if(adminProject != null){
				 adminProjectService.delete(adminProject);
				if(UserPlanContants.FOLLOW.equals(adminProject.getIsFollow())){
					adminProject.setIsFollow(UserPlanContants.UN_FOLLOW);
					updateUserPlanState(userPlanList,UserPlanContants.UN_FOLLOW,login);
				}
				
			}else{//新增操作
				adminProject = new AdminProject();
				adminProject.setProject(project);
				adminProject.setFollowAdmin(login);
				adminProject.setIsFollow(UserPlanContants.FOLLOW);//关注
				updateUserPlanState(userPlanList,UserPlanContants.FOLLOW,login);
				adminProjectService.saveOrUpdate(adminProject);
			}
		
			if(UserPlanContants.FOLLOW.equals(adminProject.getIsFollow())){
				return "关注成功!";
			}else{
				return "取消关注成功!";
			}
		}catch(Exception e){
			if(UserPlanContants.FOLLOW.equals(adminProject.getIsFollow())){
				throw new PersonalException("关注失败!");		
			}else{
				throw new PersonalException("取消关注失败!");	
			}
		}
		
	}
	
	public void updateUserPlanState(List<UserPlan> userPlanList,String state,Admin login){
		if(userPlanList != null){
			for(UserPlan userPlan : userPlanList){
				Map<String,Object>  param = new HashMap<String,Object>();
				param.put("adminId", login.getId());
				param.put("userPlanId", userPlan.getId());
				AdminUserPlan  adminUserPlan =adminUserPlanService.getAdminUserPlan(param);
				if(adminUserPlan != null){
					if(UserPlanContants.UN_FOLLOW.equals(state)){//项目如果取消关注
						adminUserPlanService.delete(adminUserPlan);
					}
				}else{
					if(UserPlanContants.FOLLOW.equals(state)){
						adminUserPlan = new AdminUserPlan();
						adminUserPlan.setUserPlan(userPlan);
						adminUserPlan.setFollowAdmin(login);
						adminUserPlan.setIsFollow(state);
						adminUserPlanService.saveOrUpdate(adminUserPlan);
					}
					
				}
				
			}
		}
		
	}

	/*  @see com.rzrk.service.ProjectService#checkIfNameExists(com.rzrk.entity.Admin, java.lang.String)  */
	@Override
	public Boolean checkIfNameExists(Project project, String name) {
		return projectDao.checkIfNameExists(project, name);
	}

}
