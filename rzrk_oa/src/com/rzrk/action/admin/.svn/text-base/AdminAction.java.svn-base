/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rzrk.common.contract.ContractParserUser;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.JobLevel;
import com.rzrk.entity.Role;
import com.rzrk.service.AdminService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.JobLevelService;
import com.rzrk.service.JobService;
import com.rzrk.service.RoleService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.util.CommonUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;

/**
 * 后台Action类 - 人员
 */

@ParentPackage("admin")
public class AdminAction extends BaseAdminAction {

	private static final long serialVersionUID = -6296393115930477663L;
	
	private Admin admin;
	private List<Role> roleList;
	//根据部门查用户的条件；
	private String deparmentId;

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	@Resource(name = "jobServiceImpl")
	private JobService jobService;
	@Resource(name = "jobLevelServiceImpl")
	private JobLevelService jobLevelService;
	@Resource(name = "deparmentServiceImpl")
	private DeparmentService deparmentService;
	@Resource 
	ContractParserUser contractParserUser;
	@Resource
	private RootContractCategoryService rootContractCategoryService;
	
	private List<Job> viceJobList;
	
	private List<Job> mainJobList;
	
    private List<Deparment> deparmentList;	
	
	public List<Deparment> getDeparmentList() {
		return deparmentList;
	}

	public void setDeparmentList(List<Deparment> deparmentList) {
		this.deparmentList = deparmentList;
	}

	public List<Job> getMainJobList() {
		return mainJobList;
	}

	public void setMainJobList(List<Job> mainJobList) {
		this.mainJobList = mainJobList;
	}

	public List<Job> getViceJobList() {
		return viceJobList;
	}

	public void setViceJobList(List<Job> viceJobList) {
		this.viceJobList = viceJobList;
	}

	// 是否已存在 ajax验证
	public String checkUsername() {
		String username = admin.getUsername();
		if (adminService.isExistByUsername(username)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	
	

	public String getDeparmentId() {
		return deparmentId;
	}

	public void setDeparmentId(String deparmentId) {
		this.deparmentId = deparmentId;
	}

	// 添加
	public String add() {
		List<Job> jobList = jobService.getAllList();
		List<JobLevel> jobLevelList = jobLevelService.getAllList();
		List<Deparment> deparmentList = deparmentService.getAllList();
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < deparmentList.size(); i++ ){
			Deparment temp = deparmentList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        Set<Job> deparmentJob = temp.getDeparmentJob();
	        
	        JSONArray jsonJobObj = new JSONArray();
	        for (Iterator iter = deparmentJob.iterator(); iter.hasNext();) {
	        	Map<String,Object> mapJob = new HashMap<String,Object>(); 
	        	Job job = (Job) iter.next();
	        	mapJob.put("id", job.getId());
	        	mapJob.put("name", job.getJobName());
	        	jsonJobObj.add(mapJob);
	        }
	        map.put("id", temp.getId());
	        map.put("name", temp.getName());
	        
	        Map<String,Object> jobMap = new HashMap<String,Object>(); 
	        jobMap.put("id", temp.getId());
	        jobMap.put("jobList", jsonJobObj);
	        map.put("data", jobMap);
	        if(null != temp.getParent()){
	        	map.put("parent", temp.getParent().getId());
	        }else{
	        	map.put("parent", "");
	        }
	        

	        jsonObj.add(map);
		}
		
		getRequest().setAttribute("jsonObj", jsonObj);
		
		
		JSONArray jsonArray = CommonUtil.getJobJsonArray(jobList);
        getRequest().setAttribute("jsonArray", jsonArray);
		
		getRequest().setAttribute("jobList", jobList);
		getRequest().setAttribute("jobLevelList", jobLevelList);
		getRequest().setAttribute("deparmentList", deparmentList);
		getRequest().setAttribute("mariageStatusList", Admin.MarriageStatus.values());
		getRequest().setAttribute("degreeEnumList", Admin.DgreeEnum.values());
		return INPUT;
	}
	
	/**
	 * 设置用户权限
	 * @return
	 */
	public String setAdminAuthority(){
		admin = adminService.load(id);
		//角色权限
		Set<String> grantedAuthorities = new HashSet<String>();
		//二级分类
		Set<String> contractCategoryList = new HashSet<String>();
		//项目 
		Set<String> projectList = new HashSet<String>();
		for (Role role : admin.getRoleSet()) {
				grantedAuthorities.addAll(role.getAuthorityList());
				contractCategoryList.addAll(role.getCcList());
				projectList.addAll(role.getPcList());
		}

		getRequest().setAttribute("superAuthorities", roleService.getSuperRole().getAuthorityList());

		this.getRequest().setAttribute("contractCategoryList", JsonUtil.toJson(contractCategoryList));
		this.getRequest().setAttribute("projectList", JsonUtil.toJson(projectList));

		this.getRequest().setAttribute("authorityList", JsonUtil.toJson(grantedAuthorities));
		this.getRequest().setAttribute("rootContractCagetoryList", rootContractCategoryService.getAllList());;
		
		if(StringUtils.isEmpty(deparmentId)){
			List<Deparment> deparmentList = deparmentService.getRootDeparment();
			if(deparmentList != null && deparmentList.size() > 0 ){//获取首个根部门
				deparmentId = deparmentList.get(0).getId();
			}
		}
		this.getRequest().setAttribute("deparmentId",deparmentId);
		return "authority";
	}


	
	@InputConfig(resultName = "error")
	public String saveAuthority() throws Exception {

		Admin tempAdmin = adminService.get(admin.getId());
		List<String> authorityList = (admin.getAuthorityList()==null? new ArrayList<String> (): admin.getAuthorityList());
		authorityList.add(Role.ROLE_BASE);
		tempAdmin.setAuthorityList(authorityList);
		tempAdmin.setCcList(admin.getCcList());
		tempAdmin.setPcList(admin.getPcList());
		adminService.update(tempAdmin);
		logInfo = "修改人物权限: " + tempAdmin.getName();
		redirectUrl = "admin!list.action?deparmentId="+deparmentId;
		return SUCCESS;
	}
	// 编辑
	public String edit() {
		admin = adminService.get(id);
		List<Job> jobList = jobService.getAllList();
		List<JobLevel> jobLevelList = jobLevelService.getAllList();
		List<Deparment> deparmentList = deparmentService.getAllList();
		
		
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < deparmentList.size(); i++ ){
			Deparment temp = deparmentList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        Set<Job> deparmentJob = temp.getDeparmentJob();
	        
	        JSONArray jsonJobObj = new JSONArray();
	        for (Iterator iter = deparmentJob.iterator(); iter.hasNext();) {
	        	Map<String,Object> mapJob = new HashMap<String,Object>(); 
	        	Job job = (Job) iter.next();
	        	mapJob.put("id", job.getId());
	        	mapJob.put("name", job.getJobName());
	        	jsonJobObj.add(mapJob);
	        }
	        map.put("id", temp.getId());
	        map.put("name", temp.getName());
	        
	        Map<String,Object> jobMap = new HashMap<String,Object>(); 
	        jobMap.put("id", temp.getId());
	        jobMap.put("jobList", jsonJobObj);
	        map.put("data", jobMap);
	        
	        if(null != temp.getParent()){
	        	map.put("parent", temp.getParent().getId());
	        }else{
	        	map.put("parent", "");
	        }
	        

	        jsonObj.add(map);
		}
		
		getRequest().setAttribute("jsonObj", jsonObj);
		
		JSONArray jsonArray = CommonUtil.getJobJsonArray(jobList);
        getRequest().setAttribute("jsonArray", jsonArray);

		getRequest().setAttribute("jobList", jobList);
		getRequest().setAttribute("jobLevelList", jobLevelList);
		getRequest().setAttribute("deparmentList", deparmentList);
		getRequest().setAttribute("mariageStatusList", Admin.MarriageStatus.values());
		getRequest().setAttribute("degreeEnumList", Admin.DgreeEnum.values());
		return INPUT;
	}

	// 列表
	public String list() {
		//pager = adminService.findPager(pager);
		String departmentId = (String) getRequest().getAttribute("deparmentId");
		Deparment department = deparmentService.get(departmentId);
		getRequest().setAttribute("department", department);
		return LIST;
	}
	
	/**
	 * 获取人员信息
	 */
	public String getAdminList(){
		List<Admin> adminList  = null;
		//获取所有的人员信息
		adminList = adminService.getAllListExceptOutOfJob();
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < adminList.size(); i++ ){
		    Admin temp = adminList.get(i);
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
	        map.put("contractType", temp.getContractType());
	        map.put("remark", temp.getRemark());
	        String deparments = "";
	        for(Deparment tempDe : temp.getDeparmentSet()){
	        	deparments = deparments + " " + tempDe.getName();
	        }
	        map.put("deparmentSet", deparments);
	        
	        String mainJobSet = "";
	        for(Job tempDe : temp.getMainJobSet()){
	        	mainJobSet = mainJobSet + " " + tempDe.getJobName();
	        }
	        map.put("jobSet", mainJobSet);
	        map.put("age", new Date().getYear() - DateUtils.parseDate(temp.getBirthDate(), "yyyy-MM-dd HH:mm:ss").getYear());
	        map.put("sex", temp.getSex());
	        map.put("birthDate", temp.getBirthDate());
	        map.put("officePhone", temp.getOfficePhone());
	        map.put("telephone", temp.getTelephone());
	        map.put("manType", temp.getManType());
	        map.put("url", temp.getUrl());
	        
	        map.put("jobLevel", temp.getJobLevel().getName());
	        if (temp.getLoginDate() != null){
	        	map.put("loginDate", DateUtils.formatDate(temp.getLoginDate(), "yyyy-MM-dd HH:mm:ss"));
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
		
		 Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("total", pager.getTotalCount()); 
	        map.put("rows", jsonObj); 
	        
			return ajax(map);
	}
	
	
	@SuppressWarnings("unchecked")
	public String getAjaxList(){
		processAjaxPagerRequestParameter();
		pager = adminService.findPager(pager,deparmentId);
		List<Admin> adminList = (List<Admin>) pager.getResult();
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < adminList.size(); i++ ){
		    Admin temp = adminList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        Object ms = temp.getMarriageStatus();
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
	        
	        map.put("contractType", temp.getContractType());
	        map.put("remark", temp.getRemark());
	        String deparments = "";
	        for(Deparment tempDe : temp.getDeparmentSet()){
	        	deparments = deparments + " " + tempDe.getName();
	        }
	        map.put("deparmentSet", deparments);
	        
	        String mainJobSet = "";
	        for(Job tempDe : temp.getMainJobSet()){
	        	mainJobSet = mainJobSet + " " + tempDe.getJobName();
	        }
	        map.put("jobSet", mainJobSet);
	        if (StringUtils.isNotBlank(temp.getBirthDate())){
		        map.put("age",  new Date().getYear() - DateUtils.parseDate(temp.getBirthDate(), "yyyy-MM-dd").getYear());
	        }else{
		        map.put("age",  "-");
	        }
	        
	        
	        map.put("jobLevel", temp.getJobLevel() ==  null? "-" : temp.getJobLevel().getName());
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
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        String mapstr = JsonUtil.toJson(jsonObj.get(0));
		return ajax(map);
	}
	
	
	@SuppressWarnings("unchecked")
	public String getAllAjaxList(){
		processAjaxPagerRequestParameter();
		List<Admin> adminList = adminService.getAllListExceptOutOfJob();
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < adminList.size(); i++ ){
		    Admin temp = adminList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        Object ms = temp.getMarriageStatus();
	        map.put("id", temp.getId()); 
	        map.put("username", temp.getUsername());
	     
	        map.put("name", temp.getName());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        String mapstr = JsonUtil.toJson(jsonObj.get(0));
		return ajax(map);
	}
	
	
	/**
	 * 获取部门下的人员信息
	 * @return
	 */
	public String getPersonAjaxList(){
		 Map<String,Object> resultMap = new HashMap<String,Object>(); 
		 JSONArray jsonObj = new JSONArray();
	
		List<Admin> adminList = null;
		try{
			adminList = adminService.getDeparmentList(this.getLoginAdmin());
		}catch(Exception e){
			adminList = new ArrayList<Admin>();
		}
	
			
		
		
		for(int i = 0; i < adminList.size(); i++ ){
		    Admin temp = adminList.get(i);
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
	        if(temp.getJobLevel() != null){
	        	map.put("jobLevel", temp.getJobLevel().getName());
	        }else{
	        	map.put("jobLevel", null);
	        }
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

	// 删除
	public String delete() {
		if (ids.length >= adminService.getTotalCount()) {
			return ajax(Status.error, "请至少保留一个人员,删除失败!");
		}
		StringBuffer logInfoStringBuffer = new StringBuffer();
		for (String id : ids) {
			Admin admin = adminService.load(id);
			logInfoStringBuffer.append(admin.getUsername() + " ");
		}
		try {
			adminService.delete(ids);
			logInfo = "删除用户: "+logInfoStringBuffer.toString();
			return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo = "删除用户失败: " +e.getMessage();
			return ajax(Status.error, "删除失败，请检查是否被【部门，项目，项目计划，工作流】等数据引用!");   //modified by huanghui ; 2015/7/9
		}
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!")
		},
		emails = {
			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		try{
			if (adminService.isExistByUsername(admin.getUsername())) {
				return ajax(Status.error, "用户名已存在!");
			}
			
			if (roleList == null || roleList.size() == 0) {
				return ajax(Status.error, "人员角色不允许为空!");
			}
			admin.setUsername(admin.getUsername().toLowerCase());
			admin.setLoginFailureCount(0);
			admin.setIsAccountLocked(false);
			admin.setIsAccountExpired(false);
			admin.setIsCredentialsExpired(false);
			admin.setRoleSet(new HashSet<Role>(roleList));
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			admin.setPassword(passwordMd5);
			admin.setMainJobSet(new HashSet<Job>(mainJobList));
			if (null == viceJobList){
				viceJobList = new ArrayList<Job>();
			}
			admin.setViceJobSet(new HashSet<Job>(viceJobList));
			admin.setDeparmentSet(new HashSet<Deparment>(deparmentList));
			adminService.save(admin);
			logInfo = "添加人员: " + admin.getUsername();
			redirectUrl = "admin!list.action";
			return ajax(Status.success, "保存成功!");
		}catch(Exception e){
			return ajax(Status.error, "保存失败!");
		}

	}

	// 更新
	/*@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") },
		emails = {
			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
		}
	)
	@InputConfig(resultName = "error")*/
	public String update() {
		try{
			if (admin.getDuplicateSortDeal() == 1){
				Admin maxSortNoJob = adminService.getMaxSortNo();
				if (null != maxSortNoJob && maxSortNoJob.getId().equals(id)){
					admin.setSortNo(maxSortNoJob.getSortNo());
				}else if (null != maxSortNoJob && admin.getSortNo() <= maxSortNoJob.getSortNo()){
					admin.setSortNo(maxSortNoJob.getSortNo() + 1);
				}
			}
			
			
			Admin persistent = adminService.load(id);
			if (roleList == null || roleList.size() == 0) {
				addActionError("管理角色不允许为空!");
				return ERROR;
			}
			admin.setRoleSet(new HashSet<Role>(roleList));
			if (StringUtils.isNotEmpty(admin.getPassword())) {
				String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
				persistent.setPassword(passwordMd5);
			}
			admin.setMainJobSet(new HashSet<Job>(mainJobList));
			if (null == viceJobList){
				viceJobList = new ArrayList<Job>();
			}
			admin.setViceJobSet(new HashSet<Job>(viceJobList));
			admin.setDeparmentSet(new HashSet<Deparment>(deparmentList));
			BeanUtils.copyProperties(admin, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp", "authorities","queryHistorySet","viewlayerSet","contractCategoryLogSet","contractLogSet","authorityListStore","contractCategoryList","projectCategoryList"});
			adminService.update(persistent);
			contractParserUser.removeCache(persistent);
			logInfo = "编辑人员: " + admin.getUsername();
			redirectUrl = "admin!list.action";
			return ajax(Status.success,"更新成功");
		}catch(Exception e){
			return ajax(Status.error,"编辑失败");
		}

	}
	
	// 获取所有管理权限集合
	public List<Role> getAllRoleList() {
		return roleService.getAllList();
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getAjaxList4BuiltIn(){
		
		List<Admin> adminList = adminService.getAllList();
		JSONArray jsonArray = new JSONArray();
		for(Admin admin :adminList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", admin.getName());
			jsonArray.add(jsonObj);
		}
		return ajax(jsonArray);
	}

	
	// 根据部门查询用户列表
	public String listDep() {
		List<Deparment> deparmentList = deparmentService.getRootDeparment();
		String rootId = "";
		if(deparmentList != null && deparmentList.size() > 0 ){//获取首个根部门
			rootId = deparmentList.get(0).getId();
		}
		getRequest().setAttribute("rootId", rootId);
		return "list_dep";
	}
	
	
	/**
	 * 获取当前用户部门名称
	 * @return
	 */
	public String getLoginAdminDeparmentName(){
		Admin admin = getLoginAdmin();
		Set<Deparment> deps = admin.getDeparmentSet();
		if(CollectionUtils.isNotEmpty(deps)){
			return ajax(deps.iterator().next().getName());
		}else{
			return ajax("");
		}
	}

	/**
	 * 获取当前用户部门名称
	 * @return
	 */
	public String getLoginAdminJobName(){
		Admin admin = getLoginAdmin();
		Set<Job> jobLevel = admin.getMainJobSet();
		if(CollectionUtils.isNotEmpty(jobLevel)){
			return ajax(jobLevel.iterator().next().getJobName());
		}else{
			return ajax("");
		}
	}

	/**
	 * 获取当前用户部门名称
	 * @return
	 */
	public String getLoginAdminName(){
		Admin admin = getLoginAdmin();
		if(admin!=null){
			return ajax(admin.getName());
		}else{
			return ajax("");
		}
	}
	
	/**
	 * 获取人员详细信息
	 */
	public String getAdminById(){
		String id = getRequest().getParameter("id");
		Admin admin = adminService.get(id);
		List<JobLevel> jobLevelList = jobLevelService.getAllList();
		
		getRequest().setAttribute("admin", admin);
		getRequest().setAttribute("jobLevelList", jobLevelList);
		getRequest().setAttribute("mariageStatusList", Admin.MarriageStatus.values());
		getRequest().setAttribute("degreeEnumList", Admin.DgreeEnum.values());
		return "detail";
	}

}