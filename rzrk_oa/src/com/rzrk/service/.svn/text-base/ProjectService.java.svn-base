package com.rzrk.service;

import java.util.List;
import java.util.Map;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Project;

public interface ProjectService extends BaseService<Project, String> {

	void update(Project persistent, Project project, Admin loginAdmin,List<Admin> adminList);

	void deleteByProjectId(String[] ids);
	
	public void  findPager(Pager pager,Map<String,Object> queryMap);
	public String projectFollow(Admin login,String id,Project project);

	/**
	 * check if name exists
	 * @param loginAdmin
	 * @param name
	 * @return
	 * @author songkez
	 * @since  2015-9-6 
	 */
	Boolean checkIfNameExists(Project project, String name);

}
