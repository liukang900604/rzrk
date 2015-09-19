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
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.entity.JobLevel;
import com.rzrk.entity.Project;
import com.rzrk.service.JobLevelService;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class JobLevelAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private JobLevel jobLevel;

	@Resource(name = "jobLevelServiceImpl")
	private JobLevelService jobLevelService;
	
	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer();
		for(String temp : ids){
			JobLevel deleteProject = jobLevelService.get(temp);
			logInfoStringBuffer.append(deleteProject.getName() +  " ");
		}
		try {
			jobLevelService.delete(ids);
			logInfo = "删除岗位级别: " + logInfoStringBuffer.toString();
			return ajax(Status.success, "删除成功!");

		} catch (Exception e) {
			logInfo = "删除岗位级别: " + e.getMessage();
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}
	
	public String add() {
		return INPUT;
	}
	
	public String edit() {
		jobLevel = jobLevelService.load(id);
		return INPUT;
	}
	
	public String save(){
		if (jobLevel.getDuplicateSortDeal() == 1){
			JobLevel maxSortNoDpr = jobLevelService.getMaxSortNo();
			if (null != maxSortNoDpr && jobLevel.getSortNo() <= maxSortNoDpr.getSortNo()){
				jobLevel.setSortNo(maxSortNoDpr.getSortNo() + 1);
			}
		}
		jobLevelService.save(jobLevel);
		logInfo = "添加岗位级别: " + jobLevel.getName();

		return ajax(Status.success, "保存成功!");
	}

	// 列表
	public String list() {
		//pager = logService.findPager(pager);
		return LIST;
	}
	
	public String checkJobLevelName(){
		String name = jobLevel.getName();
		if (jobLevelService.isExistByName(name)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	
	@InputConfig(resultName = "error")
	public String update() {

		if (jobLevel.getDuplicateSortDeal() == 1){
			JobLevel maxSortNoDpr = jobLevelService.getMaxSortNo();
			if (null != maxSortNoDpr && maxSortNoDpr.getId().equals(id)){
				jobLevel.setSortNo(maxSortNoDpr.getSortNo());
			}else if (null != maxSortNoDpr && jobLevel.getSortNo() <= maxSortNoDpr.getSortNo()){
				jobLevel.setSortNo(maxSortNoDpr.getSortNo() + 1);
			}
		}
		
		
		JobLevel persistent = jobLevelService.load(id);
		BeanUtils.copyProperties(jobLevel, persistent, new String[] {"id", "createDate", "modifyDate", "name"});

		jobLevelService.update(persistent);
		logInfo = "编辑岗位级别: " + persistent.getName();
		redirectUrl = "job_level!list.action";
		return SUCCESS;
	}

	
	public String getAjaxList() {
		processAjaxPagerRequestParameter();
		pager = jobLevelService.findPager(pager);
		List<JobLevel> logList = (List<JobLevel>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < logList.size(); i++ ){
			JobLevel temp = logList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("name", temp.getName());
	        map.put("code", temp.getCode());
	        map.put("duplicateSortDeal", temp.getDuplicateSortDeal());
	        map.put("sortNo", temp.getSortNo());
	        map.put("status", temp.getStatus());
	        map.put("description", temp.getDescription());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}

	public JobLevel getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(JobLevel jobLevel) {
		this.jobLevel = jobLevel;
	}



}