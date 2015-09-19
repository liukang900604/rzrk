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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;

import com.rzrk.contants.WorkFlowContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.entity.WorkFlowPoint;
import com.rzrk.entity.WorkFlowType;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.JobService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.service.WorkFlowFormService;
import com.rzrk.service.WorkFlowHistoryService;
import com.rzrk.service.WorkFlowPointService;
import com.rzrk.service.WorkFlowService;
import com.rzrk.service.WorkFlowTypeService;
import com.rzrk.service.WorkService;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.StrUtil;


/**
 * 工作流Action(工作流类型、工作流表单、工作流流程)
 * @author kang.liu
 */

@ParentPackage("admin")
public class WorkFlowAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

//	private String id; //接收主键
	private String workId; //工作流程主键
	private WorkFlowType workFlowType;//封装工作流类型
	private WorkFlowForm workFlowForm;//封装工作流表单
	private WorkFlow    workFlow;  //封装工作流流程
	private WorkFlowPoint    workFlowPoint;  //封装工作流节点
//	private String[]   ids;//接收获取的人员
	private String   userArray;//用户id数组
	private String   userNameArray;//用户全名称id
	private String    pointArray;//节点id
	private String   nameArray;//用户名称缩写
	private String   banchPointArray;//是否是多条件节点
	private String  conditionArray;//条件关键字
	private String  searchArray;//节点关键字 部门经理
	private String  formKeyArray;//表单关键字
	private List<Admin>  adminList;
	private Work work; //接收我的工作
	/**
	 * 工作流接口service
	 */
	@Resource
	private WorkFlowTypeService workFlowTypeService;
	/**
	 * 工作流表单service
	 */
	@Resource
	private WorkFlowFormService workFlowFormService;
	
	/**
	 * 工作流流程 service
	 */
	@Resource
	private WorkFlowService workFlowService;
	
	/**
	 * 工作流节点service
	 */
	@Resource
	private WorkFlowPointService workFlowPointService;
	
	/**
	 * 用户管理service
	 */
	@Resource
	private AdminService adminService;
	
	/**
	 * 我的工作service
	 */
	@Resource
	private WorkService workService;
	
	@Resource
	private RootContractCategoryService rootContractCategoryService;
	
	/**
	 * 二级分类service
	 */
	@Resource
	private ContractCategoryService  contractCategoryService;
	
	/**
	 *岗位接口 
	 */
	@Resource
	private JobService jobService;
	
	/**
	 * 历史流程接口
	 */
	@Resource
	private WorkFlowHistoryService workFlowHistoryService;
	
	/**
	 * 部门接口
	 */
	@Resource
	private DeparmentService deparmentService;
	/**
	 * 工作流类型
	 * @return
	 */
	public String getWorkFlowTypeList(){
		return "typeList";
	}
	
	
	/**
	 * 工作流表单
	 * @return
	 */
	public String getWorkFlowFormList(){
		return "formList";
	}
	
	
	/**
	 * 工作流流程
	 * @return
	 */
	public String list(){
		return LIST;
	}
	
	/**
	 * 工作流节点定义
	 */
	
	public String pointDefinedList(){
		this.getRequest().setAttribute("workId", id);
		return "pointlist";
	}
	
	/**
	 * 获取工作流类型结果结果
	 * @return
	 */
	public String getAjaxWorkFlowTypeList(){
		
		processAjaxPagerRequestParameter();
		pager.setSearchBy("typeName");
		/*String value = null;
		try {
		if(pager.getKeyword() != null){
			value = new String(pager.getKeyword().getBytes("ISO-8859-1"),"UTF-8");
		}else{
			value = "";
		}
		 
		} catch (UnsupportedEncodingException e) {
		 value = "";
		}
		pager.setKeyword(value);*/
		pager = workFlowTypeService.findPager(pager);
		List<WorkFlowType> alist = (List<WorkFlowType>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < alist.size(); i++ ){
			WorkFlowType temp = alist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId()); 
	        map.put("typeName", temp.getTypeName());
	        if(StringUtils.isNotEmpty(temp.getIsDelete())){
	        	 map.put("isDelete", temp.getIsDelete());
	        }else{
	        	 map.put("isDelete", WorkFlowContants.DELETE);
	        }
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
		return ajax(map);
		
	}
	
	public String workFlowdeFine(){
		return "define";
	}
	
	/**
	 * 获取工作流表单集合
	 * @return
	 */
	public String getAjaxWorkFlowFormList(){
		
		processAjaxPagerRequestParameter();
		pager.setSearchBy("formName");
	/*	String value = null;
		try {
		if(pager.getKeyword() != null){
			value = new String(pager.getKeyword().getBytes("ISO-8859-1"),"UTF-8");
		}else{
			value = "";
		}
		 
		} catch (UnsupportedEncodingException e) {
		 value = "";
		}
		pager.setKeyword(value);*/
		pager = workFlowFormService.findPager(pager);
		List<WorkFlowForm> alist = (List<WorkFlowForm>) pager.getResult();
			
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < alist.size(); i++ ){
			WorkFlowForm temp = alist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId()); 
	        map.put("formName", temp.getFormName());
	        if(StringUtils.isNotEmpty(temp.getIsDelete())){
	        	 map.put("isDelete", temp.getIsDelete());
	        }else{
	        	 map.put("isDelete", WorkFlowContants.DELETE);
	        }
	        map.put("contractName", temp.getContractName());
	        map.put("createDate", DateUtil.formatDate(temp.getCreateDate(), "yyyy-MM-dd"));
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
		return ajax(map);
		
	}
    
	
	/**
	 * 获取工作流流程集合
	 * @return
	 */
	public String getAjaxList(){
		
		processAjaxPagerRequestParameter();
		pager.setSearchBy("flowName");
		Map<String,Object> numberParam = new HashMap<String,Object>();//查询语句 参数接收容器   非模糊查询
		numberParam.put("isHistory", WorkFlowContants.CURRENT_FLOW);
		pager.setNumberParam(numberParam);
		/*String value = null;
		try {
		if(pager.getKeyword() != null){
			value = new String(pager.getKeyword().getBytes("ISO-8859-1"),"UTF-8");
		}else{
			value = "";
		}
		 
		} catch (UnsupportedEncodingException e) {
		 value = "";
		}
		pager.setKeyword(value);*/
		pager = workFlowService.findPager(pager);
		List<WorkFlow> alist = (List<WorkFlow>) pager.getResult();
			
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < alist.size(); i++ ){
			WorkFlow temp = alist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId()); 
	        map.put("flowName", temp.getFlowName());//流程名称
	        map.put("flowContent", temp.getFlowContent());//流程介绍
	        map.put("formName", temp.getFlowForm().getFormName());//表单名称
	        map.put("typeName", temp.getFlowType().getTypeName());//流程类型
	        if(StringUtils.isNotEmpty(temp.getIsDelete())){
	        	 map.put("isDelete", temp.getIsDelete());
	        }else{
	        	 map.put("isDelete", WorkFlowContants.DELETE);
	        }
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
		return ajax(map);
		
	}
	
	
	/**
	 * 获取工作流节点结果
	 * @return
	 */
	public String getAjaxWorkFlowPointList(){
		
		processAjaxPagerRequestParameter();
		pager.setSearchBy("pointName");
	/*	String value = null;
		try {
		if(pager.getKeyword() != null){
			value = new String(pager.getKeyword().getBytes("ISO-8859-1"),"UTF-8");
		}else{
			value = "";
		}
		 
		} catch (UnsupportedEncodingException e) {
		 value = "";
		}
		pager.setKeyword(value);*/
		Map<String, Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(workId)){
		 param.put("workFlow.id", workId);
		}
		pager.setNumberParam(param);
		pager = workFlowPointService.findPager(pager);
		List<WorkFlowPoint> alist = (List<WorkFlowPoint>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < alist.size(); i++ ){
			WorkFlowPoint temp = alist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId()); 
	        map.put("pointSort", temp.getPointSort());
	        map.put("pointName", temp.getPointName());
	        if(StringUtils.isNotEmpty(temp.getPointLocation())){
	        	if("0".equals(temp.getPointLocation())){
	        		map.put("pointLocation", "开始");
	        	}else if("1".equals(temp.getPointLocation())){
	        		map.put("pointLocation", "中间段");
	        	}else if("2".equals(temp.getPointLocation())){
	        		map.put("pointLocation", "结束");
	        	}
	        }else{
	        	map.put("pointLocation", "");
	        }
	        
	        map.put("nextPonit", temp.getNextPonit());
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
		return ajax(map);
		
	}
	/**
	 * 新增工作流类型 
     */
	public String addWorkFlowType() {
		workFlowType = new WorkFlowType();
		return "typeinput";
	}
	
	
	/**
	 * 修改工作流类型 
     */
	public String editWorkFlowType() {
		if(StringUtils.isNotEmpty(id)){
			workFlowType = workFlowTypeService.get(id);
		}else{
			workFlowType = new WorkFlowType();
		}
		
		return "typeinput";
	}
	/**
	 * 新增工作流表单 
     */
	public String addWorkFlowForm() {
		workFlowForm = new WorkFlowForm();
		ArrayList<?> categoryTree= rootContractCategoryService.getListWithChildAndFields();
		String categoryTreeStr = JsonUtil.toJson(categoryTree);
		getRequest().setAttribute("categoryTree", categoryTreeStr);
		return "forminput";
	}
	
	
	/**
	 * 修改工作流表单
     */
	public String editWorkFlowForm() {
		ArrayList<?> categoryTree= rootContractCategoryService.getListWithChildAndFields();
		String categoryTreeStr = JsonUtil.toJson(categoryTree);
		getRequest().setAttribute("categoryTree", categoryTreeStr);
		if(StringUtils.isNotEmpty(id)){
			workFlowForm = workFlowFormService.get(id);
		}else{
			workFlowForm = new WorkFlowForm();
		}
		
		return "forminput";
	}
	
	
	/**
	 * 新增工作流流程
     */
	public String add() {
		List<WorkFlowForm> formList = workFlowFormService.getWorkFlowFormList();//工作流表
		List<WorkFlowType> typeList = workFlowTypeService.getWorkFlowTypeList();//工作流类型
		//遍历表单
		
			Map<String,Object> result = new HashMap<String,Object>();
			Map<String,Object> perSonResult = new HashMap<String,Object>();
			Map<String,Object> jobResult  = new HashMap<String,Object>();
			
			for(WorkFlowForm form : formList){
				result.put(form.getId(), this.getFieleByFormId(form.getId()));
				perSonResult.put(form.getId(), this.getPersonFieleByFormId(form.getId()));
			}
			
			List<Job> jobList = jobService.getKeyJobList();
			 if(jobList != null){
				 for(Job job : jobList){
					 Map<String,Object> tempJob = new HashMap<String,Object>();
					 tempJob.put("name", job.getJobName());
					 jobResult.put(job.getJobName(), tempJob);
				 }
			 }
			 
		/*	List<Deparment> deparmentList =  deparmentService.getAllList();
			if(deparmentList != null){
				 for(Deparment temp : deparmentList){
					 Map<String,Object> tempJob = new HashMap<String,Object>();
					 String keyName =  StringUtils.defaultIfEmpty(temp.getDeparmentAlisa(), temp.getName());
					 tempJob.put("name",keyName);
					 jobResult.put(temp.getName(), tempJob);
					 
				 }
			 }*/
			JSONObject jobObj = JSONObject.fromObject(jobResult);
			JSONObject jsonObj = JSONObject.fromObject(result);
			JSONObject personObj = JSONObject.fromObject(perSonResult);
		
		this.getRequest().setAttribute("jobObj", jobObj);
	    this.getRequest().setAttribute("personObj", personObj);
		this.getRequest().setAttribute("jsonObj", jsonObj);
		this.getRequest().setAttribute("isEdit", false);
		this.getRequest().setAttribute("formList", formList);
		this.getRequest().setAttribute("typeList", typeList);
		return INPUT;
	}
	
	
	
	/**
	 * 修改工作流流程
     */
	public String edit() {
		List<WorkFlowForm> formList = workFlowFormService.getWorkFlowFormList();//工作流表
		List<WorkFlowType> typeList = workFlowTypeService.getWorkFlowTypeList();//工作流类型
		//遍历表单
	
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> perSonResult = new HashMap<String,Object>();
		Map<String,Object> jobResult = new HashMap<String,Object>();
		for(WorkFlowForm form : formList){
			result.put(form.getId(), this.getFieleByFormId(form.getId()));
			perSonResult.put(form.getId(), this.getPersonFieleByFormId(form.getId()));
		}
		
		 List<Job> jobList = jobService.getKeyJobList();
		 if(jobList != null){
			 for(Job job : jobList){
				 Map<String,Object> tempJob = new HashMap<String,Object>();
				 tempJob.put("name", job.getJobName());
				 jobResult.put(job.getJobName(), tempJob);
			 }
		 }
		/*List<Deparment> deparmentList =  deparmentService.getAllList();
		if(deparmentList != null){
			 for(Deparment temp : deparmentList){
				 Map<String,Object> tempJob = new HashMap<String,Object>();
				 String keyName =  StringUtils.defaultIfEmpty(temp.getDeparmentAlisa(), temp.getName());
				 tempJob.put("name",keyName);
				 jobResult.put(temp.getName(), tempJob);
				 
			 }
		 }*/
		JSONObject jobObj = JSONObject.fromObject(jobResult);
		JSONObject jsonObj = JSONObject.fromObject(result);
		JSONObject personObj = JSONObject.fromObject(perSonResult);
		
		this.getRequest().setAttribute("jobObj", jobObj);
		this.getRequest().setAttribute("jsonObj", jsonObj);
		this.getRequest().setAttribute("personObj", personObj);
		this.getRequest().setAttribute("isEdit", true);
		this.getRequest().setAttribute("formList", formList);
		this.getRequest().setAttribute("typeList", typeList);
		if(StringUtils.isNotEmpty(id)){
			workFlow = workFlowService.get(id);
			if(workFlow != null){
				this.getRequest().setAttribute("flowPointList", workFlow.getWorkFlowPointSet());
			}
		
		}else{
			workFlow = new WorkFlow();
		}
		return INPUT;
	}
	
	
	/**
	 * 获取表单数据字段
	 * @return
	 */
	public List<String> getFieleByFormId(String id){
		List<String> resultList = new ArrayList<String>();
		if(StringUtils.isEmpty(id)){
			return resultList;
		}
		WorkFlowForm  form = workFlowFormService.get(id);
		if(form == null){
			return resultList;
		}
	
		String formContent = "<div>"+form.getFormContent()+"</div>";
		Document doc = Jsoup.parse(formContent, "UTF-8");
		Elements cidElem = doc.select(".contractCategory.cfield");
		if(cidElem.size() > 0){
			for(Element element : cidElem){
				if(!element.attr("class").contains(WorkFlowContants.NUMBER)){//过滤主键字段
					//if(StringUtils.isEmpty(element.attr("builtsign"))){
						resultList.add(element.attr("field"));
					//}
				}
				
			}
		} 
		return resultList;
	}
	
	
	/**
	 * 获取人物相关表单数据字段
	 * @return
	 */
	public Map<String,Object> getPersonFieleByFormId(String id){
		Map<String,Object> resultList = new HashMap<String,Object>();
		if(StringUtils.isEmpty(id)){
			return resultList;
		}
		WorkFlowForm  form = workFlowFormService.get(id);
		if(form == null){
			return resultList;
		}
	
		String formContent = "<div>"+form.getFormContent()+"</div>";
		Document doc = Jsoup.parse(formContent, "UTF-8");
		Elements cidElem = doc.select(".contractCategory.cfield");
		if(cidElem.size() > 0){
			for(Element element : cidElem){
				if(!element.attr("class").contains(WorkFlowContants.NUMBER)){//过滤主键字段
					if(StringUtils.isNotEmpty(element.attr("builtsign")) && WorkFlowContants.USER_NAME.equals(element.attr("builtsign"))){
						Map<String,Object> temp = new HashMap<String,Object>();
						temp.put("name", element.attr("field"));
						resultList.put(element.attr("field"), temp);
					}
				}
				
			}
		} 
		return resultList;
	}
		
	/**
	 * 新增工作流类节点
     */
	public String addWorkFlowPoint() {
		this.getRequest().setAttribute("workId", workId);
		workFlowPoint = new WorkFlowPoint();
		this.getRequest().setAttribute("isEdit", false);
		return "pointinput";
	}
	
	
	/**
	 * 修改工作流节点
     */
	public String editWorkFlowPoint() {
		List<Admin> adminList = new ArrayList<Admin>();
		this.getRequest().setAttribute("workId", workId);
		if(StringUtils.isNotEmpty(id)){
			workFlowPoint = workFlowPointService.get(id);
			adminList.addAll(workFlowPoint.getWorkFlowSet());
		}else{
			workFlowPoint = new WorkFlowPoint();
		}
		this.getRequest().setAttribute("isEdit", true);
		this.getRequest().setAttribute("adminList", adminList);
		return "pointinput";
	}
	
	
	
	
	

	/**
	 * 保存工作流类型
	 * @return
	 */
    public String saveWorkFlowType(){
    	
    	if(StringUtils.isEmpty(workFlowType.getTypeName())){
			return ajax(Status.error, "类型不能为空!");
		}
    	//检查类型是否唯一
		List<WorkFlowType> flowTypeList = workFlowTypeService.checkWorkFlowTypeName(workFlowType.getTypeName());
    	if(StringUtils.isEmpty(workFlowType.getId())){//新增
    		  if(flowTypeList != null && flowTypeList.size() > 0){
    			return ajax(Status.error, "类型已存在!");
    		  }
    		try{
    			workFlowType.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
    			workFlowType.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
    			workFlowTypeService.save(workFlowType);
    			logInfo = "添加工作流类型: " + workFlowType.getTypeName();
    			return ajax(Status.success, "保存成功!");
    		}catch(Exception e){
    			e.printStackTrace();
    			return ajax(Status.error, "保存失败!");
    		}
    		
    	}else{//更新
    		WorkFlowType  tmp = workFlowTypeService.get(workFlowType.getId());
    		 if(flowTypeList != null && flowTypeList.size() > 0){
    			 for(WorkFlowType type : flowTypeList){
    				 //如果id 不相等
    				 if(!tmp.getId().equals(type.getId())){
    					 return ajax(Status.error, "类型已存在!");
    				 }
    			 }
      		  }
    		try{
    			tmp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
    			tmp.setTypeName(workFlowType.getTypeName());
    			workFlowTypeService.update(tmp);
    			logInfo = "修改工作流类型: " + tmp.getTypeName();
    			return ajax(Status.success, "保存成功!");
    		}catch(Exception e){
    			return ajax(Status.error, "保存失败!");
    		}
    		
    	}
    }
    
	/**
	 * 保存工作流表单
	 * @return
	 */
    public String saveWorkFlowForm(){
    	
    	if(StringUtils.isEmpty(workFlowForm.getFormName())){
			return ajax(Status.error, "类型不能为空!");
		}
    	
    	analyzeContractCategoryId(workFlowForm);
    	if(StringUtils.isNotEmpty(workFlowForm.getContractCategoryId())){
    		ContractCategory contractCategory =	contractCategoryService.get(workFlowForm.getContractCategoryId());
    		if(contractCategory != null){
    			workFlowForm.setContractName(contractCategory.getName());
    		}
    	}
    	
    	
    	//检查类型是否唯一
		List<WorkFlowForm> flowTypeList = workFlowFormService.checkWorkFlowFormName(workFlowForm.getFormName());
    	if(StringUtils.isEmpty(workFlowForm.getId())){//新增
    		  if(flowTypeList != null && flowTypeList.size() > 0){
    			return ajax(Status.error, "表单名已存在!");
    		  }
    		try{
    			workFlowForm.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
    			workFlowForm.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
    			workFlowFormService.save(workFlowForm);
    			logInfo = "添加工作流表单: " + workFlowForm.getFormName();
    			return ajax(Status.success, "保存成功!");
    		}catch(Exception e){
    			e.printStackTrace();
    			return ajax(Status.error, "保存失败!");
    		}
    		
    	}else{//更新
    		WorkFlowForm  temp = workFlowFormService.get(workFlowForm.getId());
    		 if(flowTypeList != null && flowTypeList.size() > 0){
    			 for(WorkFlowForm type : flowTypeList){
    				 //如果id 不相等
    				 if(!temp.getId().equals(type.getId())){
    					 return ajax(Status.error, "表单名已存在!");
    				 }
    			 }
      		  }
    		try{
    			temp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
    			temp.setFormContent(workFlowForm.getFormContent());
    			temp.setFormName(workFlowForm.getFormName());
    			temp.setContractName(workFlowForm.getContractName());
    			temp.setContractCategoryId(workFlowForm.getContractCategoryId());
    			workFlowFormService.update(temp);
    			logInfo = "修改工作流表单: " + temp.getFormName();
    			return ajax(Status.success, "保存成功!");
    		}catch(Exception e){
    			return ajax(Status.error, "保存失败!");
    		}
    		
    	}
    }
	private void analyzeContractCategoryId(WorkFlowForm workFlowForm) {
		if(StringUtils.isNotBlank(workFlowForm.getFormContent())){
			Document doc = Jsoup.parse(workFlowForm.getFormContent(), "UTF-8");
			Elements cidElem = doc.select(".contractCategory.cid");
			
			if(cidElem.size()>0){
				String contractCategoryId = cidElem.attr("contract_category_id");
				if(StringUtils.isNotBlank(contractCategoryId)){
					workFlowForm.setContractCategoryId(contractCategoryId);
				}
			}
		}
	}
	/**
	 * 整理老数据的表单的contractCategoryId
	 * @return
	 */
	@Deprecated
    public String initWorkFlowFormContractCategoryId(){
    	List<WorkFlowForm> formList = workFlowFormService.getAllList();
    	for(WorkFlowForm workFlowForm : formList){
    		analyzeContractCategoryId(workFlowForm);
    		workFlowFormService.update(workFlowForm);
    	}
		return ajax(Status.success, "整理表单成功!");
    }    
    /**
	 * 保存工作流
	 * @return
	 */
    public String save(){
    	
    	if(StringUtils.isEmpty(workFlow.getId())){
    		return insert();
    	}else{
    		return update();
    	}
    }
    
 
    /**
     * 新增工作流
     * @return
     */
    public String insert(){
    	
    	/*if(StringUtils.isEmpty(pointArray) || StringUtils.isEmpty(userNameArray) || StringUtils.isEmpty(userArray) || StringUtils.isEmpty(nameArray)){//如果没建立节点
     		return ajax(Status.error, "请建立节点!");
     	}*/
    	if(StringUtils.isEmpty(workFlow.getFlowName())){
			return ajax(Status.error, "工作流名称不能为空!");
		}
    	//检查类型是否唯一
		List<WorkFlow> flowList = workFlowService.checkWorkFlowName(workFlow.getFlowName());
		  if(flowList != null &&  flowList.size() > 0){
  			return ajax(Status.error, "流程名称已存在!");
  		  }
		  
	     	
  		try{
  			getPointList(workFlow);
  			//workFlow.setWorkFlowPointSet(new HashSet<WorkFlowPoint>(pointList));//保存工作流节点
  			workFlow.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
  			workFlow.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
  			workFlowService.save(workFlow);
  			logInfo = "新增工作流流程: " + workFlow.getFlowName();
  			return ajax(Status.success, "保存成功!");
  		}catch(PersonalException e){
			return ajax(Status.error, e.getMessage());
		}catch(Exception e){
  			return ajax(Status.error, "工作流流程保存失败!");
  		}
		
    }
    
    

    
    /**
     * 保存工作流
     * @return
     */
    public String update(){
    	if(StringUtils.isEmpty(workFlow.getFlowName())){
			return ajax(Status.error, "工作流名称不能为空!");
		}
    	//检查类型是否唯一
		List<WorkFlow> flowList = workFlowService.checkWorkFlowName(workFlow.getFlowName());
		//WorkFlow  temp = workFlowService.get(workFlow.getId());
		 if(flowList != null && flowList.size() > 0){
			 for(WorkFlow type : flowList){
				 //如果id 不相等
				 if(!workFlow.getId().equals(type.getId())){
					 return ajax(Status.error, "类型已存在!");
				 }
			 }
 		  }
	     
	     	WorkFlow persistent = workFlowService.get(workFlow.getId());
	     	
		try{
			//先将流程数据copy到流程历史表中再更新流程
    		WorkFlow history = new WorkFlow();
    		BeanUtils.copyProperties(persistent, history);
    		history.setId(null);
    		history.setCurrentFlowId(persistent.getId());//当前流程id
    		history.setFlowForm(null);
    		history.setFlowType(null);
    		history.setIsHistory(2);
    		Set<WorkFlowPoint> historyPoint = new HashSet<WorkFlowPoint>();
    		//设置历史节点id
    		for(WorkFlowPoint point : persistent.getWorkFlowPointSet()){
    			WorkFlowPoint tempPoint = new WorkFlowPoint(); 
    			Set<Admin> adminSet = new HashSet<Admin>();
    			adminSet.addAll(point.getWorkFlowSet());
    			BeanUtils.copyProperties(point,tempPoint);
    			tempPoint.setId(null);//历史节点 ID  上一节点id-版本号
    			tempPoint.setWorkFlowPointId(point.getId()+"-"+persistent.getVersion());
    			tempPoint.setWorkFlow(history);
    			tempPoint.setWorkFlowSet(adminSet);
    			historyPoint.add(tempPoint);
    		}
    		history.setWorkFlowPointSet(historyPoint);
    		
			getPointList(persistent);
			//BeanUtils.copyProperties(workFlow, persistent, new String[] {"id","createDate", "modifyDate","WorkFlowPointSet"});
			//persistent.getWorkFlowPointSet().clear();
			//persistent.setWorkFlowPointSet(new HashSet<WorkFlowPoint>(pointList));//工作流节点
			persistent.setIsEmail(workFlow.getIsEmail());//是否邮件
			persistent.setIsMsg(workFlow.getIsMsg());//是否短信
			persistent.setFlowContent(workFlow.getFlowContent());//工作流内容简介
			persistent.setFlowName(workFlow.getFlowName());//工作流名称
			persistent.setFlowForm(workFlow.getFlowForm());//工作流表单
			persistent.setFlowType(workFlow.getFlowType());//工作流类型
			persistent.setModifyDate(new Date(System.currentTimeMillis()));
			if(persistent.getVersion() == null){
				persistent.setVersion(0L);
			}else{
				persistent.setVersion(persistent.getVersion()+1);
			}
			workFlowService.updateWorkFlow(persistent, history);
			logInfo = "修改工作流流程: " + persistent.getFlowName();
			return ajax(Status.success, "工作流流程保存成功!");
		}catch(PersonalException e){
			return ajax(Status.error, e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ajax(Status.error, "数据被引用!");
		}
    }
    
    
    /**
     * 获取我的工作流节点
     * @param flow
     * @return
     * @throws Exception 
     */
    public WorkFlow getPointList(WorkFlow flow) throws Exception{
    	
       // List<WorkFlowPoint>  pointList = new ArrayList<WorkFlowPoint>();//工作流节点数组
    	if(flow != null && StringUtils.isNotEmpty(flow.getId())){
    		flow.getWorkFlowPointSet().clear();
    		//workFlowService.saveOrUpdate(flow);
    	}
    	
    	if(!(StringUtils.isEmpty(pointArray) || StringUtils.isEmpty(userNameArray) || StringUtils.isEmpty(userArray) || StringUtils.isEmpty(nameArray) || StringUtils.isEmpty(banchPointArray) || StringUtils.isEmpty(conditionArray))){

			String[] userId = userArray.trim().split("、");//用户id数组
	     	String[] pointId = pointArray.trim().split("、");//节点数组
	     	String[] userName = userNameArray.trim().split("、");//节点全名
	     	String[] name = nameArray.trim().split("、");//名字缩写
	     	String[] banchPoint = banchPointArray.trim().split("、");//分支节点 1:分支  2：非分支
	     	String[]  condition = conditionArray.trim().split("、"); //用户条件分支  
	     	String[]  search  =  searchArray.trim().split("、"); //节点关键字
	     	String[]  formKey = formKeyArray.trim().split("、");//表单关键字
			WorkFlowForm  form = workFlowFormService.get(flow.getFlowForm().getId());
			if(form == null){
				throw new PersonalException("流程表单不存在!");
			}
			String formContent = "<div>"+form.getFormContent()+"</div>";
			Document doc = Jsoup.parse(formContent, "UTF-8");
			
	     	//组装节点
	     	for(int i = 0; i < pointId.length; i++){
	     		
	     		WorkFlowPoint temp = null;
	     		if(StringUtils.isNotEmpty(pointId[i]) && !WorkFlowContants.BEGIN.equals(pointId[i])){
	     			temp = workFlowPointService.get(pointId[i]);
	     			temp.setId(pointId[i]);//说明是更新
	     		}else{
	     			temp = new WorkFlowPoint();
	     			temp.setCreateDate(new Date(System.currentTimeMillis()));//创建时间 
	     			temp.setPointLocation(WorkFlowContants.BETWEEN);//中间节点
	     			temp.setCheckSelect(WorkFlowContants.BETWEEN);//默认审批人选择
	         		temp.setIsFile(WorkFlowContants.BETWEEN);//是否支持附件 默认支持
	         		temp.setIsAdd(WorkFlowContants.END);//工作流程创建的
	     		}
	     		
	     		
	     		//中间节点从2开始
	     		temp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
	     		temp.setPointSort(String.valueOf(i+2));//节点序号 
	     		temp.setNextPonit(String.valueOf(i+3)); 
	     		temp.setWorkFlow(flow);//工作流程
	     		temp.setIsBranch(banchPoint[i]);//是否为分支节点
	     		
	     		if(WorkFlowContants.BRANCH.equals(banchPoint[i])){//如果为分支节点
	     			if(StringUtils.isNotEmpty(condition[i]) && !WorkFlowContants.BEGIN.equals(condition[i])){
	     				temp.setShowCondition(condition[i]);
	     				
	     				JSONArray jsonArray =  JSONArray.fromObject(condition[i]);
	     				StringBuffer userCondition = new StringBuffer();//多分支用户条件
	     				for(int j = 0; j < jsonArray.size(); j++){//遍历分支
	     					JSONArray tempJson =jsonArray.getJSONArray(j);//获取条件
	     					if(StringUtils.isNotEmpty(tempJson.getString(2))){
	     						temp.setKeyName(tempJson.getString(2));
	     					}else{
	     						throw new PersonalException("分支节点条件名称未定义,请定义条件名称!");
	     					}
	     					//校验条件节点在流程表单中是否定义
	     					StringBuffer queryBuffer = new StringBuffer();
	     					queryBuffer.append("[field=").append(tempJson.getString(2)).append("]");
	     					Elements cidElem = doc.select(queryBuffer.toString());
	     					if(cidElem.size() <= 0){
	     						throw new PersonalException("流程表单未定义分支条件,请检查流程表单");
	     					}
	     					
	     					StringBuffer pointCondition = new StringBuffer();//节点条件
	     			        //无用户代表空节点
	     					if(StringUtils.isNotEmpty(tempJson.getString(5))){//当前分支的用户
	     						JSONArray userArray = tempJson.getJSONArray(5);//获取用户信息
	     						for(int k = 0; k < userArray.size(); k++){
	     							pointCondition.append(userArray.getString(k)).append("_");
	     						}
	     						pointCondition = new StringBuffer(pointCondition.substring(0,pointCondition.length()-1));
	     					}
	     						
	     						if(!(StringUtils.isEmpty(tempJson.getString(0)) && StringUtils.isEmpty(tempJson.getString(4)))){
	     							pointCondition.append(":");
	     							if(StringUtils.isNotEmpty(tempJson.getString(0))){//第一个条件值
	     								if("<".equals(tempJson.getString(1)) || "≤".equals(tempJson.getString(1))){//检测条件值
	     									if(!StrUtil.isNumeric(tempJson.getString(0))){
												throw new PersonalException("流程无法解析,分支节点条件<或≤只能填写整数!");
											}
	     								}
	     								
		     							pointCondition.append(tempJson.getString(1)).append(tempJson.getString(0)).append("_");
		     						}else{
		     							pointCondition.append("_");
		     						}
		     						if(StringUtils.isNotEmpty(tempJson.getString(4))){//第二个条件值
		     							if("<".equals(tempJson.getString(3)) || "≤".equals(tempJson.getString(3))){//检测条件值
	     									if(!StrUtil.isNumeric(tempJson.getString(4))){
	     										throw new PersonalException("流程无法解析,分支节点条件<或≤只能填写整数!");
											}
	     								}
		     							pointCondition.append(tempJson.getString(3)).append(tempJson.getString(4)).append("_");
		     						}
		     						pointCondition = new StringBuffer(pointCondition.substring(0,pointCondition.length()-1));	
		     					    pointCondition.append(":");
		     					    
		     					 
		     					   if(StringUtils.isNotEmpty(tempJson.getString(6))){//关键字
		     						  JSONArray keyArray = tempJson.getJSONArray(6);//获取关键字信息
		     						    for(int k = 0; k < keyArray.size(); k++){
			     							pointCondition.append(keyArray.getString(k)).append("_");
			     						}
		     					   }
		     						userCondition.append(pointCondition.substring(0,pointCondition.length()-1)).append("").append(",");
	     						}else{//如果不选择条件  默认为空
	     							throw new PersonalException("某个分支节点未设置条件,请检查分支节点条件!");
	     						}
	     						
	     						
	     					/*else{
	     						throw new PersonalException("某个分支节点无用户,请选择用户!");
	     					}*/
	     				}
	     				temp.setUserCondition(userCondition.substring(0,userCondition.length()-1));
	     					
	     			}
	     		}else{
	     		
	     			if(StringUtils.isNotEmpty(search[i]) && !WorkFlowContants.BEGIN.equals(search[i])){//节点关键字
	     				temp.setSearchName(search[i]);//节点关键字
	     			}
	     			if(StringUtils.isNotEmpty(formKey[i]) && !WorkFlowContants.BEGIN.equals(formKey[i])){//表单关键字
	     				temp.setSearchName(formKey[i]);//表单关键字
	     				temp.setFormKeyName(formKey[i]);
	     			}
	     			
	     			if(StringUtils.isNotEmpty(userId[i]) && !WorkFlowContants.BEGIN.equals(userId[i])){
		     			//List<Admin>  adminList = new ArrayList<Admin>();//人员
		     			temp.setUserId(userId[i]);//用户id
		     			temp.getWorkFlowSet().clear();
		     			String[] adminArray = userId[i].trim().split(",");
		     			for(String obj:adminArray){
		     				Admin tempAdmin = new Admin();
		     				tempAdmin.setId(obj);
		     				//adminList.add(tempAdmin);
		     				temp.getWorkFlowSet().add(tempAdmin);
		     			}
		     		
		     			//temp.setWorkFlowSet(new HashSet<Admin>(adminList));//节点用户
		     		}
	     		}
	     		if(StringUtils.isNotEmpty(userName[i]) && !WorkFlowContants.BEGIN.equals(userName[i])){//用户全名
	     			temp.setUserName(userName[i]);
	     			temp.setPointName(userName[i]);//节点名称
	     		}
	     		/*if(StringUtils.isNotEmpty(name[i]) && !WorkFlowContants.BEGIN.equals(name[i])){//用户缩写
	     			temp.setName(name[i]);
	     		}*/
	     		flow.getWorkFlowPointSet().add(temp);
	     	}
     		
     	}
    	return flow;
    }
    
    /**
  	 * 保存流程节点
  	 * @return
  	 */
      public String saveFlowPoint(){
   
     	if(StringUtils.isEmpty(pointArray) || StringUtils.isEmpty(userNameArray) || StringUtils.isEmpty(userArray) || StringUtils.isEmpty(nameArray)  ){//如果没建立节点
     		return ajax(Status.error, "请建立节点!");
     	}
     	String[] userId = userArray.trim().split("、");//用户id数组
     	String[] pointId = pointArray.trim().split("、");//节点数组
     	String[] userName = userNameArray.trim().split("、");//节点全名
     	String[] name = nameArray.trim().split("、");//名字缩写
     	String[] banchPoint = banchPointArray.trim().split("、");//分支节点 1:分支  2：非分支
     	
     	List<WorkFlowPoint>  pointList = new ArrayList<WorkFlowPoint>();//工作流节点数组
     	//组装节点
     	for(int i = 0; i < pointId.length; i++){
     		WorkFlowPoint temp = new WorkFlowPoint();
     		if(StringUtils.isNotEmpty(pointId[i]) && !WorkFlowContants.BEGIN.equals(pointId[i])){
     			temp.setId(pointId[i]);//说明是更新
     		}else{
     			temp.setCreateDate(new Date(System.currentTimeMillis()));//创建时间 
     			temp.setPointLocation(WorkFlowContants.BETWEEN);//中间节点
     			temp.setCheckSelect(WorkFlowContants.BETWEEN);//默认审批人选择
         		temp.setIsFile(WorkFlowContants.BETWEEN);//是否支持附件 默认支持
         		temp.setIsAdd(WorkFlowContants.END);//工作流程创建的
     		}
     		temp.setPointName("节点"+(i+1));
     		
     		//中间节点从2开始
     		temp.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
     		temp.setPointSort(String.valueOf(i+2));//节点序号 
     		temp.setNextPonit(String.valueOf(i+3)); 
     		temp.setWorkFlow(workFlow);//工作流程
     		temp.setIsBranch(banchPoint[i]);//设置节点是否为分支属性
     		if(StringUtils.isNotEmpty(userId[i]) && !WorkFlowContants.BEGIN.equals(userId[i])){
     				List<Admin>  adminList = new ArrayList<Admin>();//人员
         			String[] adminArray = userId[i].trim().split(",");
         			for(String obj:adminArray){
         				Admin tempAdmin = new Admin();
         				tempAdmin.setId(obj);
         				adminList.add(tempAdmin);
         			}
         			temp.setWorkFlowSet(new HashSet<Admin>(adminList));//节点用户
     			
     		}
     		if(StringUtils.isNotEmpty(userName[i]) && !WorkFlowContants.BEGIN.equals(userName[i])){//用户全名
     			temp.setUserName(userName[i]);
     		}
     		if(StringUtils.isNotEmpty(name[i]) && !WorkFlowContants.BEGIN.equals(name[i])){//用户缩写
     			temp.setName(name[i]);
     		}
     		
     		pointList.add(temp);
     		
     	}
     	
     	try{
 			workFlowPointService.saveManyEntity(pointList);
 			return ajax(Status.success, "节点流程创建成功!");
 		}catch(Exception e){
 			return ajax(Status.error, "节点流程创建失败!");
 		}
      }
    
    
   
    
      //工作流节点定义
      public String pointdefinde(){
    	  this.getRequest().setAttribute("workId", workId);
    	  return "pointdefinde";
      }
      
      
    /**
     * 删除工作流类型
     * @return
     */
    public String deleteFlowType(){
    	if(StringUtils.isEmpty(id)){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	WorkFlowType workFlowType = workFlowTypeService.get(id);
    	if(workFlowType == null){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	try{
    		workFlowTypeService.delete(id);
    		this.logInfo = "删除工作流类型 ："+workFlowType.getTypeName();
    		return ajax(Status.success, "删除成功!");
    	}catch(Exception e){
    		return ajax(Status.error, "类型被引用，不能删除!");
    	}
    	
    }
    
    /**
     * 删除工作流表单
     * @return
     */
    public String deleteFlowForm(){
    	if(StringUtils.isEmpty(id)){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	WorkFlowForm workFlowForm = workFlowFormService.get(id);
    	if(workFlowForm == null){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	try{
    		workFlowFormService.delete(id);
    		this.logInfo = "删除工作流表单："+workFlowForm.getFormName();
    		return ajax(Status.success, "删除成功!");
    	}catch(Exception e){
    		return ajax(Status.error, "表单被引用，不能删除!");
    	}
    	
    }
    
    /**
     * 删除工作流流程
     * @return
     */
    public String delete(){
    	if(StringUtils.isEmpty(id)){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	WorkFlow workFlow = workFlowService.get(id);
    	if(workFlow == null){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	try{
    		workFlowService.delete(workFlow);
    		this.logInfo = "删除工作流流程："+workFlow.getFlowName();
    		return ajax(Status.success, "删除成功!");
    	}catch(Exception e){
    		return ajax(Status.error, "流程被引用，不能删除!");
    	}
    	
    }
    
    /**
     * 删除工作流节点
     * @return
     */
    public String deleteWorkFlowPoint(){
    	if(StringUtils.isEmpty(id)){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	WorkFlowPoint workFlowPoint = workFlowPointService.get(id);
    	if(workFlowPoint == null){
    		return ajax(Status.error, "需要删除的信息不存在!");
    	}
    	try{
    		workFlowPointService.delete(workFlowPoint);
    		return ajax(Status.success, "删除成功!");
    	}catch(Exception e){
    		return ajax(Status.error, "节点被引用，不能删除!");
    	}
    	
    }

    /**
     * 修改单节点数据
     * @return
     */
    public String changePointData(){
    	try{
    		workFlowPointService.changePointData();
    		return ajax(Status.success, "更改成功!");
    	}catch(Exception e){
    		return ajax(Status.error, "更换失败!");
    	}
    	
    }
    
    
  
	public WorkFlowType getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(WorkFlowType workFlowType) {
		this.workFlowType = workFlowType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WorkFlowForm getWorkFlowForm() {
		return workFlowForm;
	}

	public void setWorkFlowForm(WorkFlowForm workFlowForm) {
		this.workFlowForm = workFlowForm;
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public WorkFlowPoint getWorkFlowPoint() {
		return workFlowPoint;
	}

	public void setWorkFlowPoint(WorkFlowPoint workFlowPoint) {
		this.workFlowPoint = workFlowPoint;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public String getUserArray() {
		return userArray;
	}

	public void setUserArray(String userArray) {
		this.userArray = userArray;
	}

	public String getUserNameArray() {
		return userNameArray;
	}

	public void setUserNameArray(String userNameArray) {
		this.userNameArray = userNameArray;
	}

	public String getPointArray() {
		return pointArray;
	}

	public void setPointArray(String pointArray) {
		this.pointArray = pointArray;
	}

	public String getNameArray() {
		return nameArray;
	}

	public void setNameArray(String nameArray) {
		this.nameArray = nameArray;
	}

	public String getBanchPointArray() {
		return banchPointArray;
	}

	public void setBanchPointArray(String banchPointArray) {
		this.banchPointArray = banchPointArray;
	}

	public String getConditionArray() {
		return conditionArray;
	}

	public void setConditionArray(String conditionArray) {
		this.conditionArray = conditionArray;
	}

	public String getSearchArray() {
		return searchArray;
	}

	public void setSearchArray(String searchArray) {
		this.searchArray = searchArray;
	}

	public String getFormKeyArray() {
		return formKeyArray;
	}

	public void setFormKeyArray(String formKeyArray) {
		this.formKeyArray = formKeyArray;
	}

	






	
	
   
	
	
	
	
	

}