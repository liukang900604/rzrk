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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.common.contract.ContractParserDepartment;
import com.rzrk.common.contract.ContractParserProduct;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Project;
import com.rzrk.service.AdminService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.JobService;
import com.rzrk.util.CommonUtil;

/**
 * 后台Action类 - 日志
 */

@ParentPackage("admin")
public class DeparmentAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private Deparment deparment;

	@Resource(name = "deparmentServiceImpl")
	private DeparmentService deparmentService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "jobServiceImpl")
	private JobService jobService;
	@Resource 
	ContractParserDepartment contractParserDepartment;
	
	private List<Job> jobList;
    private List<Admin> deparmentAdmins = new ArrayList<Admin>();//部门人员
	
	public List<Admin> getDeparmentAdmins() {
		return deparmentAdmins;
	}

	public void setDeparmentAdmins(List<Admin> deparmentAdmins) {
		this.deparmentAdmins = deparmentAdmins;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer();
		for(String temp : ids){
			Deparment deleteProject = deparmentService.get(temp);
			logInfoStringBuffer.append(deleteProject.getName() +  " ");
		}
		try{
			deparmentService.delete(ids);
			logInfo = "删除部门: "+logInfoStringBuffer.toString();
			return ajax(Status.success, "删除成功!");
		}catch(Exception e){
			logInfo = "删除部门失败: " + e.getMessage();
			return ajax(Status.error, "删除失败，请检查是否被关联!");
		}
	}
	
	public String save() {
		//Boolean exsits = deparmentService.checkIfHasRootDeparment(null);
//		Deparment temp = deparmentService.getByName(deparment.getName().trim());
//		if (temp != null){
//			return ajax(Status.error, "部门名称已经存在!");
//		}
		/*if(exsits && StringUtils.isEmpty(deparment.getParent().getId())){
			return ajax(Status.error,"根部门只能有一个！");
		}*/
		if(StringUtils.isNotEmpty(deparment.getDeparmentAlisa())){//判断别名是否为空
			List<Deparment> deparmentList = deparmentService.getDeparmentListByAlisaName(deparment.getDeparmentAlisa());
			if(deparmentList != null && deparmentList.size() > 0){
				return ajax(Status.error, "主管别名已存在!"); 
			}
		}
		if (deparment.getDuplicateSortDeal() == 1){
			Deparment maxSortNoDpr = deparmentService.getMaxSortNo();
			if (null != maxSortNoDpr && deparment.getSortNo() <= maxSortNoDpr.getSortNo()){
				deparment.setSortNo(maxSortNoDpr.getSortNo() + 1);
			}
		}
		if(deparment.getParent().getId().trim().length()==0){
			deparment.setParent(null);
		}
		
		if(jobList == null){
			jobList = new ArrayList<Job>();
		}
		deparment.setDeparmentJob(new HashSet<Job>(jobList));
		
		
		
		deparmentService.saveWithAdminList(deparment,deparmentAdmins);
		logInfo = "添加部门: " + deparment.getName();

		return ajax(Status.success, "添加成功!");
	}
	
	// 添加
	public String add() {
		List<Admin> adminList = adminService.getAllList();
		
		getRequest().setAttribute("adminList", adminList);
		getRequest().setAttribute("parentDeparmentList", deparmentService.getAllList());
		List<Job> jobList = jobService.getAllList();
		getRequest().setAttribute("jobList", jobList);
		
		JSONArray jsonArray = CommonUtil.getJobJsonArray(jobList);
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("jsonArray", jsonArray); 
        
        getRequest().setAttribute("jsonArray", jsonArray);
		
		return INPUT;
	}

	
	public String checkDeparmentName(){
		String name = deparment.getName();
		if (deparmentService.isExistByName(name)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	
	
	/**
	 * 选择多人数据接口
	 * @return
	 */
  public String getDeparmentPersondData(){
	  Map<String,Object> dataMap = new HashMap<String,Object>();
	  dataMap.put("deparmentList", deparmentService.getDeparmnetList());//获取所有部门json数据
	  dataMap.put("deparmentAdminList",deparmentService.getAllDeparemntAdmin());//获取所有部门下成员json数据
	  return ajax(dataMap);
  }
	
	// 编辑
	public String edit() {
		List<Admin> adminList = adminService.getAllList();
		
		getRequest().setAttribute("adminList", adminList);
		deparment = deparmentService.get(id);
		List<Deparment> deparmentList = deparmentService.getAllList();
		if (null != deparmentList){
			
			deparmentList.remove(deparment);
		}
		
		
		getRequest().setAttribute("parentDeparmentList", deparmentList);
		
		List<Job> jobList = jobService.getAllList();
		getRequest().setAttribute("jobList", jobList);
		
		JSONArray jsonArray = CommonUtil.getJobJsonArray(jobList);
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("jsonArray", jsonArray); 
        
        getRequest().setAttribute("jsonArray", jsonArray);
        
		return INPUT;
	}

	// 列表
	public String list() {
		//pager = logService.findPager(pager);
		return LIST;
	}
	
	
	public String getPersonList() {
		//this.getRequest().setAttribute("deparementId", id);//保存部门id 
		return "person";
	}

	@InputConfig(resultName = "error")
	public String update() {
		/*Boolean exsits = deparmentService.checkIfHasRootDeparment(id);
		if(exsits && StringUtils.isEmpty(deparment.getParent().getId())){
			return ajax(Status.error,"根部门只能有一个！");
		}*/
	
		
		if(StringUtils.isNotEmpty(deparment.getDeparmentAlisa())){//判断别名是否为空
			List<Deparment> deparmentList = deparmentService.getDeparmentListByAlisaName(deparment.getDeparmentAlisa());
			if(deparmentList != null && deparmentList.size() > 0){
				for(Deparment temp : deparmentList){
					if(!temp.getId().equals(id)){
						return ajax(Status.error, "主管别名已存在!"); 
					}
				}
			}
		}
		if (deparment.getDuplicateSortDeal() == 1){
			Deparment maxSortNoDpr = deparmentService.getMaxSortNo();
			if (null != maxSortNoDpr && maxSortNoDpr.getId().equals(id)){
				deparment.setSortNo(maxSortNoDpr.getSortNo());
			}else if (null != maxSortNoDpr && deparment.getSortNo() <= maxSortNoDpr.getSortNo()){
				deparment.setSortNo(maxSortNoDpr.getSortNo() + 1);
			}
		}
		
		if(deparment.getParent().getId().trim().length()==0){
			deparment.setParent(null);
		}
		Deparment persistent = deparmentService.load(id);
	
		if(jobList == null){
			jobList = new ArrayList<Job>();
		}
		deparment.setDeparmentJob(new HashSet<Job>(jobList));
		BeanUtils.copyProperties(deparment, persistent, new String[] {"id", "createDate", "modifyDate","deparmentAdmins"});
		
		
		deparmentService.updateWithAdminList(persistent,deparmentAdmins);

		logInfo = "编辑部门: " + persistent.getName();
		contractParserDepartment.removeCache(persistent);
		redirectUrl = "deparment!list.action";
		return ajax(Status.success,"保存成功！");
	}
	
	private void pushDepartmentSortList(List<Deparment> collect,Deparment node){
		collect.add(node);
		for(Deparment dep : node.getChildDeparments()){
			pushDepartmentSortList(collect,dep);
		}
	}
	
	public void assignJson(Deparment dep,JSONArray childrenArr){
        JSONObject node = new JSONObject(); 
        node.put("id", dep.getId()); 
        node.put("name", dep.getName());
        node.put("departmentCode",dep.getDepartmentCode());
        node.put("sortNo",dep.getSortNo());
        node.put("duplicateSortDeal",dep.getDuplicateSortDeal());
        node.put("status", dep.getStatus());
        node.put("isCreateSpace", dep.getIsCreateSpace()); 
        node.put("deparmentLeader", dep.getDeparmentLeader().getName());
        node.put("peopleAmount", 0);
        node.put("departmentOtherLeader",dep.getDepartmentOtherLeader().getName());
//        node.put("deparmentAdmin", dep.getDeparmentAdmin().getName());
        node.put("description", dep.getDescription());
        node.put("deparmentAlisa", dep.getDeparmentAlisa());
        JSONArray children = new JSONArray();
        for(Deparment subDep : dep.getChildDeparments()){
        	assignJson(subDep,children);
        }
        node.put("children", children);
        childrenArr.add(node);
	}
	

	
	public String getAjaxList() {
		processAjaxPagerRequestParameter();
		pager = deparmentService.findPager(pager);
		List<Deparment> logList = (List<Deparment>) pager.getResult();
		List<Deparment> logListSort = new ArrayList<Deparment>();
		List<Deparment> rootDepList= new ArrayList<Deparment>();
		for(Deparment dep : logList){
			if(dep.getParent()==null){
				rootDepList.add(dep);
			}
		}
		Assert.notNull(rootDepList);
		JSONArray rootArr = new JSONArray();
		for(Deparment dep : rootDepList){
			assignJson(dep,rootArr);
		}
	
//		if(rootDep!=null){
//			pushDepartmentSortList(logListSort,rootDep);
//		}else{
//			logListSort = logList;
//		}
//		
//		JSONArray jsonObj = new JSONArray();
//		for(int i = 0; i < logListSort.size(); i++ ){
//			Deparment temp = logListSort.get(i);
//	        Map<String,Object> map = new HashMap<String,Object>(); 
//	        
//	        map.put("id", temp.getId()); 
//	        map.put("name", temp.getName());
//	        map.put("departmentCode",temp.getDepartmentCode());
//	        map.put("sortNo",temp.getSortNo());
//	        map.put("duplicateSortDeal",temp.getDuplicateSortDeal());
//	        map.put("status", temp.getStatus());
//	        map.put("isCreateSpace", temp.getIsCreateSpace()); 
//	        map.put("deparmentLeader", temp.getDeparmentLeader().getName());
//	        map.put("peopleAmount", 0);
//	        map.put("departmentOtherLeader",temp.getDepartmentOtherLeader().getName());
//	        map.put("deparmentAdmin", temp.getDeparmentAdmin().getName());
//	        map.put("description", temp.getDescription());
//	        if (null != temp.getParent()){
//	        	map.put("parent", temp.getParent().getName());
//	        }else{
//	        	map.put("parent", "-");
//	        }
//	        
//	        jsonObj.add(map);
//		}
//         
//        Map<String,Object> map = new HashMap<String,Object>(); 
//        
//        map.put("total", pager.getTotalCount()); 
//        map.put("rows", jsonObj); 
        
		return ajax(rootArr);
	}

	public Deparment getDeparment() {
		return deparment;
	}

	public void setDeparment(Deparment deparment) {
		this.deparment = deparment;
	}
	
	public String getAjaxDeparmentTree(){
		
		List<Deparment> deparmentList = deparmentService.getAllList();
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < deparmentList.size(); i++ ){
			Deparment temp = deparmentList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("name", temp.getName());
	        if (null != temp.getParent()){
	        	map.put("parent", temp.getParent().getId());
	        }else{
	        	map.put("parent", "");
	        }
	        jsonObj.add(map);
		}
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("root", jsonObj); 
        map.put("success", true); 
		return ajax(map);
		
	}
	
	private JSONArray getChildJson(Deparment parent){
		JSONArray jsonArray = new JSONArray();
		if(parent!=null){
			for(Deparment dep : parent.getChildDeparments()){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", dep.getId());
				jsonObj.put("text", dep.getName());
				jsonObj.put("children", getChildJson(dep));
				jsonArray.add(jsonObj);
			}
		}
		return jsonArray;
	}
	//TODO 待优化
	public String getAjaxTree4easyUi(){
		
		List<Deparment> deparmentList = deparmentService.getRootDeparment();
		JSONArray jsonRootArray = new JSONArray();
		if(deparmentList!=null){
			for(Deparment root :deparmentList){
				JSONObject jsonRoot = new JSONObject();
				jsonRoot.put("id", root.getId());
				jsonRoot.put("text", root.getName());
				jsonRoot.put("children", getChildJson(root));
				jsonRootArray.add(jsonRoot);
			}
		}
		return ajax(jsonRootArray);
	}
	
	//TODO 待优化
	private JSONArray getChildJsonBuiltIn(Deparment parent){
		JSONArray jsonArray = new JSONArray();
		if(parent!=null){
			for(Deparment dep : parent.getChildDeparments()){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", dep.getName());
				jsonObj.put("text", dep.getName());
				jsonObj.put("children", getChildJsonBuiltIn(dep));
				jsonArray.add(jsonObj);
			}
		}
		return jsonArray;
	}
	public String getAjaxTree4BuiltIn(){
		
		List<Deparment> deparmentList = deparmentService.getRootDeparment();
		JSONArray jsonRootArray = new JSONArray();
		if(deparmentList!=null){
			for(Deparment root : deparmentList){
				JSONObject jsonRoot = new JSONObject();
				jsonRoot.put("id", root.getName());
				jsonRoot.put("text", root.getName());
				jsonRoot.put("children", getChildJsonBuiltIn(root));
				jsonRootArray.add(jsonRoot);
			}
			
		}
		
		return ajax(jsonRootArray);
	}
	
	public String getTree(){
		return "tree";
	}

	
	


}