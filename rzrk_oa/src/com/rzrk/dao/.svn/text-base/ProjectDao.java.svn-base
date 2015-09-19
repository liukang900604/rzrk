/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao;


import java.util.Map;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Project;

/**
 * Project接口 - 人员
 */

public interface ProjectDao extends BaseDao<Project, String> {

	void deleteByProjectId(String[] ids);
	
	public void findPager(Pager pager,Map<String,Object> queryMap);

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