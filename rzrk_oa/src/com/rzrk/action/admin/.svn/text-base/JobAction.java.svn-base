/**

 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.common.contract.ContractParserJob;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Job;
import com.rzrk.service.AdminService;
import com.rzrk.service.JobService;
import com.rzrk.util.date.DateUtils;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class JobAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;


	@Resource(name = "jobServiceImpl")
	private JobService jobService;
	@Resource
	private ContractParserJob contractParserJob;
	/**
	 * 用户服务接口
	 */
	@Resource
	private AdminService  adminService;
	
	private Job job;
	private List<Admin> adminList = new ArrayList<Admin>();
	
	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer();
		for(String temp : ids){
			Job deleteProject = jobService.get(temp);
			logInfoStringBuffer.append(deleteProject.getJobName() +  " ");
		}
		try {
			jobService.delete(ids);
			logInfo = "删除岗位: " + logInfoStringBuffer.toString();
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			logInfo = "删除岗位失败: " + e.getMessage();
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}

	
	
	public String add() {
		 adminList = adminService.getAllList();
		getRequest().setAttribute("jobTypeList", Job.JobType.values());
		return INPUT;
	}
	
	public String edit() {
		job = jobService.load(id);
		adminList = adminService.getAllList();
		getRequest().setAttribute("jobTypeList", Job.JobType.values());
		return INPUT;
	}
	
	public String save(){
		if (job.getDuplicateSortDeal() == 1){
			Job maxSortNoJob = jobService.getMaxSortNo();
			if (null != maxSortNoJob && job.getSortNo() <= maxSortNoJob.getSortNo()){
				job.setSortNo(maxSortNoJob.getSortNo() + 1);
			}
		}
		jobService.save(job);
		logInfo = "添加岗位: " + job.getJobName();

		return ajax(Status.success, "保存成功!");
	}

	// 列表
	public String list() {
		//pager = logService.findPager(pager);
		return LIST;
	}
	
	public String checkJobName(){
		String name = job.getJobName();
		if (jobService.isExistByName(name)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	
	@InputConfig(resultName = "error")
	public String update() {
		if (job.getDuplicateSortDeal() == 1){
			Job maxSortNoJob = jobService.getMaxSortNo();
			if (null != maxSortNoJob && maxSortNoJob.getId().equals(id)){
				job.setSortNo(maxSortNoJob.getSortNo());
			}else if (null != maxSortNoJob && job.getSortNo() <= maxSortNoJob.getSortNo()){
				job.setSortNo(maxSortNoJob.getSortNo() + 1);
			}
		}
		Job persistent = jobService.load(id);
		BeanUtils.copyProperties(job, persistent, new String[] {"id", "createDate", "modifyDate"});
		jobService.update(persistent);
		contractParserJob.removeCache(persistent);
		logInfo = "编辑岗位: " + persistent.getJobName();
		redirectUrl = "job!list.action";
		return SUCCESS;
	}
	
	public String getAjaxList() {
		processAjaxPagerRequestParameter();
		pager = jobService.findPager(pager);
		List<Job> logList = (List<Job>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < logList.size(); i++ ){
			Job temp = logList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("jobName", temp.getJobName());
	        map.put("jobCode",temp.getJobCode());
	        map.put("duplicateSortDeal",temp.getDuplicateSortDeal());
	        map.put("sortNo",temp.getSortNo());
	        map.put("priorityLevel", temp.getPriorityLevel());
	        map.put("jobType", temp.getJobType());
	        map.put("status", temp.getStatus());
	        map.put("desciption", temp.getDesciption());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
	}
	
	public String getAjaxList4BuiltIn(){
		
		List<Job> jobList = jobService.getAllList();
		JSONArray jsonArray = new JSONArray();
		for(Job job :jobList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", job.getJobName());
			jsonArray.add(jsonObj);
		}
		return ajax(jsonArray);
	}

	public String personList() {
		this.getRequest().setAttribute("jobId", id);//保存岗位id 
		return "person";
	}

	
	
	
	/**
	 * 获取岗位下的人员信息
	 * @return
	 */
	public String getPersonAjaxList(){
		
		String jobId = (String) this.getRequest().getParameter("jobId");//获取岗位id 
		 Map<String,Object> resultMap = new HashMap<String,Object>(); 
		 JSONArray jsonObj = new JSONArray();
	
		//如果岗位不存在
		if(StringUtils.isEmpty(jobId)){
			resultMap.put("rows", jsonObj); 
			return ajax(resultMap);	
		}
		List<String> adminList = null;
		try{
			adminList = adminService.getAdminByJob(jobId);
		}catch(Exception e){
			adminList = new ArrayList<String>();
		}
	
			
		
		
		for(int i = 0; i < adminList.size(); i++ ){
			if(StringUtils.isEmpty(adminList.get(i))){
				continue;
			}
		    Admin temp = adminService.get(adminList.get(i));
		    if(temp == null){
		    	continue;
		    }
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("username", temp.getUsername());
	        map.put("email",temp.getEmail());
	        map.put("code",temp.getCode());
	        map.put("duplicateSortDeal",temp.getDuplicateSortDeal());
	        map.put("sortNo",temp.getSortNo());
	        map.put("name", temp.getName());
	        map.put("location", temp.getLocation());
	        map.put("minzu", temp.getMinzu());
	        map.put("politics", temp.getPolitics());
	        map.put("marriageStatus", temp.getMarriageStatus());
	        map.put("dgreeEnum", temp.getDgreeEnum());
	        map.put("graduateSchool", temp.getGraduateSchool());
	        map.put("major", temp.getMajor());
	        map.put("onBoardDate", temp.getOnBoardDate());
	        map.put("contractDueDay", temp.getContractDueDay());
	        map.put("contractDueDay2", temp.getContractDueDay2());
	        map.put("contractDueDay3", temp.getContractDueDay3());
	        map.put("contractDueDay4", temp.getContractDueDay4());
	        map.put("turnRegularDate", temp.getTurnRegularDate());
	        map.put("document", temp.getDocument());
	        map.put("jobTitle", temp.getJobTitle());
	        map.put("identification", temp.getIdentification());
	        map.put("hukouType", temp.getHukouType());
	        map.put("hukouLocation", temp.getHukouLocation());
	        map.put("resident", temp.getResident());
	        map.put("ergentCall", temp.getErgentCall());
	        map.put("quitDate", temp.getQuitDate());
	        
	        map.put("sex", temp.getSex());
	        map.put("birthDate", temp.getBirthDate());
	        map.put("officePhone", temp.getOfficePhone());
	        map.put("telephone", temp.getTelephone());
	        map.put("manType", temp.getManType());
	        map.put("url", temp.getUrl());
	        
	        map.put("jobLevel", temp.getJobLevel().getName());
	        if (temp.getLoginDate() != null){
	        	map.put("loginDate", DateUtils.formatDate(temp.getLoginDate(), "yyyy-MM-dd"));
	        }else{
	        	map.put("loginDate", "-");
	        }
	        
	        map.put("loginIp", temp.getLoginIp());
	        map.put("isAccountEnabled", temp.getIsAccountEnabled());
	        map.put("isAccountLocked", temp.getIsAccountLocked());
	        map.put("isAccountExpired", temp.getIsAccountExpired());
	        map.put("isCredentialsExpired", temp.getIsCredentialsExpired());
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd"));
	        String status = "";
	        
	        if (temp.getIsAccountEnabled() && !temp.getIsAccountLocked() && !temp.getIsAccountExpired() && !temp.getIsCredentialsExpired()){
	        	status = "<span style='color:green;'>正常</span>";
	        }else if (!temp.getIsAccountEnabled()){
	        	status = "<span style='color:green;'>未启用</span>";
	        }else if (temp.getIsAccountLocked()){
	        	status = "<span style='color:green;'>已锁定</span>";
	        }else if (temp.getIsAccountExpired()){
	        	status = "<span style='color:green;'>已过期</span>";
	        }else if(temp.getIsCredentialsExpired()){
	        	status = "<span style='color:green;'>凭证过期</span>";
	        }
	        
	        map.put("status", status);
	        	
	        jsonObj.add(map);
		}
         
     //   Map<String,Object> map = new HashMap<String,Object>(); 
        
		resultMap.put("rows", jsonObj); 
        
		return ajax(resultMap);
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}
	


}