/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.entity.Group;
import com.rzrk.entity.UserPlan;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.service.GroupService;
import com.rzrk.service.UserPlanLogService;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class UserPlanLogAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private UserPlanLog userPlanLog;

	@Resource(name = "userPlanLogServiceImpl")
	private UserPlanLogService userPlanLogService;
	
	// 删除
	public String delete() {
		userPlanLogService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}
	

	// 列表
	public String list() {
		//pager = logService.findPager(pager);
		return LIST;
	}
	
	
	public String getAjaxList() {
		processAjaxPagerRequestParameter();
		pager = userPlanLogService.findPager(pager);
		List<Group> logList = (List<Group>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < logList.size(); i++ ){
			Group temp = logList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("groupName", temp.getGroupName());
	        map.put("indexNo",temp.getIndexNo());
	        map.put("status", temp.getStatus());
	        map.put("groupType", temp.getGroupType());
	        map.put("groupPrivlege", temp.getGroupPrivlege());
	        map.put("desciption", temp.getGroupPrivlege());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}

	public UserPlanLog getUserPlanLog() {
		return userPlanLog;
	}

	public void setUserPlanLog(UserPlanLog userPlanLog) {
		this.userPlanLog = userPlanLog;
	}




}