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

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.contants.CategoryContants;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ApprovalRecord;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.Project;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowPoint;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.MailService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.SmsService;
import com.rzrk.service.SystemMessageService;
import com.rzrk.service.WorkFlowPointService;
import com.rzrk.service.WorkFlowService;
import com.rzrk.service.WorkFlowTypeService;
import com.rzrk.service.WorkService;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.FileVo;
import com.rzrk.vo.workflow.Project4workflow;
import com.rzrk.vo.workflow.Require4workflow;


/**
 * 工作Action(我的工作、工作审核 工作查询)
 * @author kang.liu
 */

@ParentPackage("admin")
public class CheckAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

    //private String id; //接收主键
	private Work work;//封装我的工作
	private String flowTypeId;//工作流类型id 
	private List<Admin>  adminList;//接收审批人
	private ApprovalRecord record;//审批记录
	private String[]  filePath;//文件真实路径
	private String[]  fileName;//文件名称
	private String fieldId;
	private String isDelete;//是否删除合同字段   1：删除  2：不删除
	private String contractCategoryId;//二级分类id
	private Project4workflow  workFlowVo; //工作项目vo
	private Require4workflow require4workflowVo;//需求Vo
	private String isView;
	/**
	 * 系统消息service
	 */
	@Resource
	private SystemMessageService systemService;
	
	/**
	 * 短信发送接口
	 */
	@Resource
	private SmsService  smsService;
	
	/**
	 * 邮件发送接口
	 */
	@Resource
	private MailService mailService;
	/**
	 * 二级分类字段service
	 */
	@Resource
	private ContractService contractService;
	/**
	 * 工作流接口service
	 */
	@Resource
	private WorkService workService;
	
	/**
	 * 工作流类型service
	 */
	@Resource
	private WorkFlowTypeService workFlowTypeService;
	
	/**
	 * 工作流节点service
	 */
	@Resource
	private WorkFlowPointService workFlowPointService;
	
	/**
	 * 工作流流程service
	 */
	@Resource
	private WorkFlowService workFlowService;
	
	/**
	 * 人员service
	 */
	@Resource
	private AdminService adminService;
	/**
	 * 部门service
	 */
	@Resource
	private DeparmentService deparmentService;
	
	/**
	 * 二级分类service
	 */
	@Resource(name = "contractCategoryServiceImpl")
	private ContractCategoryService contractCategoryService;
	
	@Resource
	ProjectService projectService;
	
	/**
	 * 创建开发审批
     */
	public String addDevelopmentWork() {
		work = new Work();
		String workFlowId = CategoryContants.PROJECT_SETUP_ID;//提交发开审批流程id
		WorkFlow workFlow = null;
		if(StringUtils.isNotEmpty(workFlowId)){
			 workFlow = workFlowService.get(workFlowId);
			
		}
		adminList = adminService.getAllList();
		this.getRequest().setAttribute("workFlow", workFlow);//特殊工作流
		return "devlopment_check";
	}
	
	
	
	/**
	 * 创建内部需求审批
     */
	public String addInsideRequestWork() {
		work = new Work();
		String workFlowId = CategoryContants.INTERNAL_REQUIREMENT_FLOW_ID;//内部需求流程id
		WorkFlow workFlow = null;
		if(StringUtils.isNotEmpty(workFlowId)){
			 workFlow = workFlowService.get(workFlowId);
			
		}
		this.getRequest().setAttribute("workFlow", workFlow);//特殊工作流
		getRequest().setAttribute("contractCategorySpecify", "REQUIREMENT_CATEGORY");
//		for(Field field : fieldlist){
			List<Project> projectList = projectService.getAllList();
			JSONArray belongProjectOpts = new JSONArray();
			for(Project project : projectList){
				JSONObject jobj = new JSONObject();
				jobj.put("id", project.getId());
				jobj.put("text", project.getName());
				jobj.put("name", project.getName());
				belongProjectOpts.add(jobj);
			}
			getRequest().setAttribute("belongProjectOpts", JsonUtil.toJson(belongProjectOpts));
		return "inside_request_check";
	}
	
	/**
	 * 创建外部需求审批
     */
	public String addExternalRequestWork() {
		work = new Work();
		String workFlowId = CategoryContants.EXTERNAL_REQUIREMENT_FLOW_ID;//外部需求流程id
		WorkFlow workFlow = null;
		if(StringUtils.isNotEmpty(workFlowId)){
			 workFlow = workFlowService.get(workFlowId);
			
		}
		this.getRequest().setAttribute("workFlow", workFlow);//特殊工作流
		getRequest().setAttribute("contractCategorySpecify", "REQUIREMENT_CATEGORY");
//		for(Field field : fieldlist){
			List<Project> projectList = projectService.getAllList();
			JSONArray belongProjectOpts = new JSONArray();
			for(Project project : projectList){
				JSONObject jobj = new JSONObject();
				jobj.put("id", project.getId());
				jobj.put("text", project.getName());
				jobj.put("name", project.getName());
				belongProjectOpts.add(jobj);
			}
			getRequest().setAttribute("belongProjectOpts", JsonUtil.toJson(belongProjectOpts));
		return "external_request_check";
	}
	
	


	// 获取需求列表列表
	public String addRequirementList() {
		
	     contractCategoryId = CategoryContants.REQUIREMENT_CATEGORY_ID;
	     this.getRequest().setAttribute("workType", "1");
//		if(!contractCategoryService.canUpdateContractRecord(contractCategoryId, getLoginAdmin())){
//			return "accessDenied";
//		}
	    String isEdit = this.getRequest().getParameter("isEdit");
	    this.getRequest().setAttribute("isEdit", isEdit);
		ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
		if(contractCategory == null){
			return "request_list";
		}
		String[] titleArr = contractCategory.getDefinitionObj().getTitles();
		String[] totalArr = new String[]{};
		totalArr = contractCategory.getDefinitionObj().getTotals();
		this.getRequest().setAttribute("requestId", this.getRequest().getParameter("requestId"));//需求列表id
		getRequest().setAttribute("contractCategoryName", contractCategory.getName());
		getRequest().setAttribute("contractCategoryTitles", JsonUtil.toJson(titleArr));
		getRequest().setAttribute("contractCategoryTotals", JsonUtil.toJson(totalArr));
		getRequest().setAttribute("titleArr", titleArr);
		return "request_list";
	}
	
	// 获取bug列表
	public String addBugList() {
		 this.getRequest().setAttribute("workType", "2");
	     contractCategoryId = CategoryContants.ONLINE_BUG_REUIREMENT_CATEGORY_ID;
//		if(!contractCategoryService.canUpdateContractRecord(contractCategoryId, getLoginAdmin())){
//			return "accessDenied";
//		}
	    String isEdit = this.getRequest().getParameter("isEdit");
	    this.getRequest().setAttribute("isEdit", isEdit);
		ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
		if(contractCategory == null){
			return "request_list";
		}
		String[] titleArr = contractCategory.getDefinitionObj().getTitles();
		String[] totalArr = new String[]{};
		totalArr = contractCategory.getDefinitionObj().getTotals();
		this.getRequest().setAttribute("requestId", this.getRequest().getParameter("requestId"));//需求列表id
		getRequest().setAttribute("contractCategoryName", contractCategory.getName());
		getRequest().setAttribute("contractCategoryTitles", JsonUtil.toJson(titleArr));
		getRequest().setAttribute("contractCategoryTotals", JsonUtil.toJson(totalArr));
		getRequest().setAttribute("titleArr", titleArr);
		return "request_list";
	}
	
	/**
	 * 创建部署提交审批
     */
	public String addDeploySubmitWork() {
		work = new Work();
		String workFlowId = CategoryContants.NORMAL_DEPLOY_ID;//部署提交审批
		WorkFlow workFlow = null;
		if(StringUtils.isNotEmpty(workFlowId)){
			 workFlow = workFlowService.get(workFlowId);
			
		}
		adminList = adminService.getAllList();
		this.getRequest().setAttribute("workFlow", workFlow);//特殊工作流
		return "deploy_submit_check";
	}
	

	/**
	 * 创建紧急部署提交审批
     */
	public String addUrgentDeploySubmitWork() {
		work = new Work();
		String workFlowId = CategoryContants.EMERGENCE_DEPLOY_ID;//紧急部署提交流程id
		WorkFlow workFlow = null;
		if(StringUtils.isNotEmpty(workFlowId)){
			 workFlow = workFlowService.get(workFlowId);
			
		}
		adminList = adminService.getAllList();
		this.getRequest().setAttribute("workFlow", workFlow);//特殊工作流
		return "urgent_deploy_submit_check";
	}
	
	
	/**
	 * 创建线上bug提交审批
     */
	public String addOnlinBugSubmitWork() {
		work = new Work();
		String workFlowId = CategoryContants.ONLINE_BUG_SUBMIT_ID;//线上bug流程id
		WorkFlow workFlow = null;
		if(StringUtils.isNotEmpty(workFlowId)){
			 workFlow = workFlowService.get(workFlowId);
			
		}
		this.getRequest().setAttribute("workFlow", workFlow);//特殊工作流
		return "online_bug_submit";
	}
	
	
	
	/*
	 * 编辑开发工作
	 */
	public String editDevelopmentWork(){
		if(StringUtils.isNotEmpty(id)){
			work = workService.get(id);
		}else{
			work = new Work();
		}
		this.getRequest().setAttribute("isEdit", true);
		WorkFlowPoint tempPoint = null;
		if(StringUtils.isNotEmpty(work.getCurrentPointId())){
			tempPoint = workFlowPointService.get(work.getCurrentPointId(),work.getVersion());
		}
		 String flowId =  workFlowService.getMatchFlowId(work.getFlowId(), work.getVersion());//找到匹配的流程  历史还是现在
		 
		 List<WorkFlowPoint> flowPoint = null;
		 if(tempPoint == null){
			 flowPoint = workFlowPointService. getFlowPointList(flowId);//获取首节点
		 }else{
			 flowPoint =	workFlowPointService.getPointListByNextPoint(tempPoint.getNextPonit(),flowId); //查找下一节点
		 }
	     if(flowPoint != null && flowPoint.size() > 0){
	    	 this.getRequest().setAttribute("flowPoint", flowPoint.get(0));
	     }
	     adminList = adminService.getAllList();

		if(work.getFileName() != null && work.getFilePath() != null){
			String[] fileName = work.getFileName().split(",");//文件名称
			String[] filePath = work.getFilePath().split(",");//文件路径
			List<FileVo> fileList = new ArrayList<FileVo>();
			 for(int i = 0; i < fileName.length; i++){
				 FileVo vo = new FileVo();
				 vo.setFileName(fileName[i]);
				 vo.setFilePath(filePath[i]);
				 fileList.add(vo);
			 }
			 this.getRequest().setAttribute("fileList",fileList); //文件对象
		}
		return "editdevelopmentwork";
	}
	
	 /**
  	 * 保存工作流节点
  	 * @return
  	 */
      public String save(){
      	//判断新增还是修改
      	if(StringUtils.isEmpty(work.getId())){
      		return insert();
      	}else{
      		return update();
      	}
      }
      
      /**
       * 组装项目数据
       */
      public String creareProjectDate(){
    	  if(work.getWorkType() != null && (WorkFlowContants.DEPLOYMENT_FLOW.equals(work.getWorkType()) )){//开发流程或确认收款流程
    	 
    		  if(workFlowVo == null){
    			  workFlowVo = new Project4workflow();
    		  }
          String requestField = (String) this.getRequest().getParameter("requestField");//需求列表Id
    	  String requestName = (String) this.getRequest().getParameter("requestName");//需求名称
    	  if(StringUtils.isEmpty(requestField) ){
    			throw new PersonalException("请选择数据!");
    	  }
    	  if(StringUtils.isEmpty(requestName)){
    		  requestName = "";
    	  }
    	  String [] requestIdArray = requestField.trim().split(",");
    	  String [] requestNameArray = requestName.trim().split(",");
    	  
    	  List<String> requestIdList = new ArrayList<String>();
    	  for(String temp : requestIdArray){
    		  requestIdList.add(temp);
    	  }
    	  workFlowVo.getRequestRowids().addAll(requestIdList);//需求id
    	  requestIdList.clear();
    	  for(String temp : requestNameArray){
    		  requestIdList.add(temp);
    	  }
    	  workFlowVo.setRequestName(requestIdList);//需求名称
    	  work.setExpand(JsonUtil.toJson(workFlowVo));//以Json格式存贮
    	 }else if(work.getWorkType() != null && (WorkFlowContants.INSIDE_REQUIRE_FLOW.equals(work.getWorkType()) || WorkFlowContants.EXTERNAL_REQUIRE_FLOW.equals(work.getWorkType()) )){
             String joinProject = (String) this.getRequest().getParameter("joinProject");//欲加入的项目
             require4workflowVo = new Require4workflow();
             require4workflowVo.setProjectId(joinProject);
       	  	work.setExpand(JsonUtil.toJson(require4workflowVo));//以Json格式存贮
    	 }
    	  return "";
      }
      
  	/**
  	 * 工作审批
  	 * @return
  	 */
  	public String getWorkCheck(){
  		return "myWorkCheck";
  	}
      
  	
	
	/**
	 * 基金管理费收款确认审批
     */
	public String getConfirmReceiptWork() {
		return "confirm_receipt_check";
	}
  	
  	
	/**
	 * 获取我的工作集合
	 * @return
	 */
	public String getAjaxWorkList(){
		
		processAjaxPagerRequestParameter();
		Admin loginAdmin = getLoginAdmin(); //获取登录账户
		//查询条件
		Map<String,Object> param = new HashMap<String,Object>();//精确查询map
		Map<String,Object> params = new HashMap<String,Object>();//模糊查询map
		String flowType  = this.getRequest().getParameter("flowType");
		pager.setAdminId(loginAdmin.getId());
		pager.setCurrentId(loginAdmin.getId());
		if(StringUtils.isNotEmpty(flowType) && WorkFlowContants.CONFIRM_RECEIPT_FLOW.equals(flowType)){
			String [] flowList = {CategoryContants.CONFIRM_RECEIPT_WORKFLOW_ID};//收款确认流程
			pager.setFlowList(flowList);//设置流程集合
		}else{
			pager.setFlowList(CategoryContants.REQUIREMENT_APPROVE_ARRAY);//设置流程集合
		}
		
		pager.setFlowType(WorkFlowContants.INSIDE_FLOW);//内置流程类型
		String spell = this.getRequest().getParameter("spell");//拼音首字母
		String flow = this.getRequest().getParameter("flow");//流程id
		String type = this.getRequest().getParameter("type");//流程类型id
		String workStatu = this.getRequest().getParameter("workStatu");//工作状态
		String dateStatu = this.getRequest().getParameter("dateStatu");//日期选择
		String beginDate = this.getRequest().getParameter("beginDate");//开始日期
		String endDate = this.getRequest().getParameter("endDate");//结束日期
		String queryType = this.getRequest().getParameter("queryType");//查询类型
		String queryText = this.getRequest().getParameter("queryText");//查询关键字
		
		if(StringUtils.isNotEmpty(spell)){//首字母查询
			if(!"ALL".equals(spell)){
				params.put("wokrCode", spell.toLowerCase());
			}
		}
		
		if(StringUtils.isNotEmpty(flow) && !"0".equals(flow)){
			param.put("flowId", flow);//流程id
		}
		if(StringUtils.isNotEmpty(type) && !"0".equals(type)){
			param.put("flowTypeId", type);//流程类型id
		}
		if(StringUtils.isNotEmpty(workStatu) && !"0".equals(workStatu)){
			param.put("status", workStatu);//状态
		}
		//日期选择
		if(StringUtils.isNotEmpty(dateStatu) ){
			if("0".equals(dateStatu)){//创建时间
				pager.setDateType("createDate");
				if(StringUtils.isNotEmpty(beginDate)){
					pager.setBeginDate(beginDate);
				}
				if(StringUtils.isNotEmpty(endDate)){
					pager.setEndDate(endDate);
				}
			}else if("1".equals(dateStatu)){//修改时间
				pager.setDateType("modifyDate");
				
				if(StringUtils.isNotEmpty(beginDate)){
					pager.setBeginDate(beginDate);
				}
				if(StringUtils.isNotEmpty(endDate)){
					pager.setEndDate(endDate);
				}
			}
		}
		
		//查询字段
		if(StringUtils.isNotEmpty(queryType)){
			if("0".equals(queryType)){//工作名称
				pager.setSearchBy("workName");
			}else if("1".equals(queryType)){//发起人
				pager.setSearchBy("admin.name");
			}else if("2".equals(queryType)){//按内容
				pager.setSearchBy("formContent");
			}else if("3".equals(queryType)){//按最后审批人
				pager.setSearchBy("lastAdmin.name");
			}
		}
		
		//查询内容
		if(StringUtils.isNotEmpty(queryText)){
			/*try {
				//pager.setKeyword(new String(queryText.getBytes("ISO-8859-1"),"UTF-8").trim());
				
			} catch (UnsupportedEncodingException e) {
				
			}*/
			pager.setKeyword(queryText);
		}
		
		pager.setNumberParam(param);
		pager.setParam(params);
		pager = workService.findPager(pager);
		
		
		List<Work> alist = (List<Work>) pager.getResult();
			
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < alist.size(); i++ ){
			Work temp = alist.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        map.put("id", temp.getId());
	        if(temp.getCurrentPointName()!= null){
	        	map.put("pointName", temp.getCurrentPointName());
	        }else{
	        	map.put("pointName", "");
	        }
	        if(StringUtils.isEmpty(temp.getStatus())){
	        	map.put("status", "");
	        	map.put("jsStatus", "0");
	        	map.put("endDate", "-");
	        }else{
	        	map.put("jsStatus", temp.getStatus());
	        	if(temp.getStatus().equals("1")){
	        		map.put("status", "取消");
	        		map.put("endDate", DateUtils.formatDateTime(temp.getEndDate()));
	        	}else if(temp.getStatus().equals("2")){
	        		map.put("status", "正常结束");
	     	        map.put("pointName", "-");
	     	       map.put("endDate", DateUtils.formatDateTime(temp.getEndDate()));
	        	}else if(temp.getStatus().equals("3")){
	        		map.put("status", "待"+temp.getCurrentAdminName()+"审批");
	        		map.put("endDate", "-");
	        	}else if(temp.getStatus().equals("4")){
	        		map.put("status", "被撤回");
	        		map.put("endDate", "-");
	        	}else{
	        		map.put("status", "");
	        		map.put("endDate", "-");
	        	}
	        }
	        if(StringUtils.isNotEmpty(temp.getFlowName())){
	        	 map.put("flowName", temp.getFlowName());//流程名称
	        }else{
	        	 map.put("flowName", "");//流程名称
	        }
	        map.put("loginAdminId", loginAdmin.getId());//当前用户
	      
	        if(temp.getAdmin() != null){
	        	 map.put("name", temp.getAdmin().getName());//流程发起人
	        	 map.put("adminId", temp.getAdmin().getId());//发起用户
	        }else{
	        	 map.put("name", "");//流程发起人
	        	 map.put("adminId", "");//发起用户
	        }
	        map.put("currentId", temp.getCurrentId() == null ? "" : temp.getCurrentId());//处理人id
	        map.put("workName", temp.getWorkName());
	       map.put("createDate", DateUtils.formatDateTime(temp.getCreateDate()));
	        if(temp.getLastAdmin() != null){
	        	 map.put("lastName", temp.getLastAdmin().getName());//最后审批人
	        }else{
	        	 map.put("lastName", "");//最后审批人
	        }
	       
	        jsonObj.add(map);
		}
        Map<String,Object> map = new HashMap<String,Object>(); 
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
		return ajax(map);
		
	}
    
      
      /**
       * 新增内置工作流
       * @return
       */
      public String insert(){
      	if(StringUtils.isEmpty(work.getWorkName())){
  			return ajax(Status.error, "工作名称不能为空!");
  		}
      	Admin loginAdmin = getLoginAdmin(); //获取登录账户
      	//检查工作名称是否唯一
  		List<Work> workList = workService.checkWorkName(work.getWorkName());
      		 if(workList != null && workList.size() > 0){
      			return ajax(Status.error, "工作名称已存在!");
      		  }
      		 String fieldId = this.getRequest().getParameter("fieldId");
      		 
      	  try{
      		  creareProjectDate();//组装项目信息
      		  	workService.insertWork(work, fileName, filePath, fieldId, loginAdmin, adminList);
      		  logInfo = "新增我的工作: " + work.getWorkName();
      		  	return ajax(Status.success, "保存成功!");
      		}catch(PersonalException e){
      			return ajax(Status.error,e.getMessage());
      		}catch(Exception e){
      			e.printStackTrace();
      			return ajax(Status.error,"保存失败!");
      		}
  		
      }
     
       
      /**
       * 编辑内置工作流
       * @return
       */
      public String update(){
      	if(StringUtils.isEmpty(work.getWorkName())){
  			return ajax(Status.error, "工作名称不能为空!");
  		}
      	
      	
      //检查工作名称是否唯一
  		List<Work> workList = workService.checkWorkName(work.getWorkName());
      	Work  temp = workService.get(work.getId());//获取工作
		     if(workList != null && workList.size() > 0){
			 for(Work type : workList){
				 //如果id 不相等
				 if(!temp.getId().equals(type.getId())){
					 return ajax(Status.error, "工作名称已存在!");
				 	}
			 	}
 		  }
		    Admin loginAdmin = this.getLoginAdmin();
      		try{
      			creareProjectDate();
      			if(StringUtils.isNotEmpty(work.getExpand())){
      				temp.setExpand(work.getExpand());
      			}
      			String str = workService.updateWork(work, temp, adminList,loginAdmin,fileName, filePath);
      			logInfo = "修改我的工作: " + work.getWorkName();
	      		return ajax(Status.success, "保存成功!");
	      		
      		}catch(PersonalException e){
      			return ajax(Status.error,e.getMessage());
      		}catch(Exception e){
      			return ajax(Status.error, "保存失败!");
      		}
  		
      }
   
      
      /**
       * 获取工作类型json数据
       * @return
       */
      public String getWorkFlowTypeList(){
      	return ajax(workService.getWorkFlowTypeList());	
      }
      
      /**
       * 获取工作流程json数据
       * @return
       */
      public String getWorkFlowList(){	
  		return ajax(workService.getWorkFlowList());	
      }
    
      
 /*     *//**
		 * 取消工作
		 * @return
		 *//*
		 public String cancleMyWork(){
			 if(StringUtils.isEmpty(id)){
					return ajax(Status.error, "查找的信息不存在!");
				}
			
			   Admin loginAdmin = getLoginAdmin(); //获取登录账户
			 
			 try{
				String str = workService.cancleMyWork(id, loginAdmin);
				if("success".equals(str)){
	      		  	  return ajax(Status.success, "取消成功!");
	      		  	}else{
	      		  	  return ajax(Status.success, "取消失败!");
	      		  	}
				}catch(Exception e){
					return ajax(Status.error, "取消失败!");
				}
			
		 }*/
      
	/*	    
			 * 查看我的工作
			 
			public String viewMyWork(){
				if(StringUtils.isNotEmpty(id)){
					work = workService.get(id);
					if(work!=null && StringUtils.isNotEmpty(work.getFormContent())){
						String fc=work.getFormContent().replace("nowrap=\"nowrap\"", "");
						work.setFormContent(fc);
					}
					List<ApprovalRecord> recordList =  new ArrayList<ApprovalRecord>(work.getRecord()); 
					this.getRequest().setAttribute("recordList", recordList);
					
				}else{
					work = new Work();
				}
				 adminList = adminService.getAllList();
				if(work.getFileName() != null && work.getFilePath() != null){
					String[] fileName = work.getFileName().split(",");//文件名称
					String[] filePath = work.getFilePath().split(",");//文件路径
					List<FileVo> fileList = new ArrayList<FileVo>();
					 for(int i = 0; i < fileName.length; i++){
						 FileVo vo = new FileVo();
						 vo.setFileName(fileName[i]);
						 vo.setFilePath(filePath[i]);
						 fileList.add(vo);
					 }
					 this.getRequest().setAttribute("fileList",fileList); //文件对象
				}
				this.getRequest().setAttribute("admin", getLoginAdmin()); //获取登录账户);
				this.pageUrl = "/WEB-INF/template/admin/work_viewmywork.ftl";
				return PAGEURL;
			}
		 
		 
			
			 * 编辑工作审批
			 
			public String editWorkCheck(){
				if(StringUtils.isNotEmpty(id)){
					work = workService.get(id);
					List<ApprovalRecord> recordList =  new ArrayList<ApprovalRecord>(work.getRecord()); 
					if(work.getWorkFlow() != null){
						this.getRequest().setAttribute("flowPointList", work.getWorkFlow().getWorkFlowPointSet());
					}
					WorkFlowPoint point = null;
					if(StringUtils.isNotEmpty(work.getCurrentPointId())){
						point = workFlowPointService.get(work.getCurrentPointId());
					}
					this.getRequest().setAttribute("currentFlowPoint", point);//当前节点
					this.getRequest().setAttribute("recordList", recordList);
					WorkFlowPoint tempPoint = null;
					if(StringUtils.isNotEmpty(work.getCurrentPointId())){
						tempPoint = workFlowPointService.get(work.getCurrentPointId());
					}
					if(tempPoint ==  null){
						tempPoint = new WorkFlowPoint();
					}
					 String flowId =  workFlowService.getMatchFlowId(work.getFlowId(), work.getVersion());//找到匹配的流程  历史还是现在
				     List<WorkFlowPoint> flowPoint =	workFlowPointService.getPointListByNextPoint(tempPoint.getNextPonit(),flowId);
				     if(flowPoint != null && flowPoint.size() > 0){
				    	 this.getRequest().setAttribute("flowPoint", flowPoint.get(0));
				     }
				     
				}else{
					work = new Work();
				}
				if(work.getFileName() != null && work.getFilePath() != null){
					String[] fileName = work.getFileName().split(",");//文件名称
					String[] filePath = work.getFilePath().split(",");//文件路径
					List<FileVo> fileList = new ArrayList<FileVo>();
					 for(int i = 0; i < fileName.length; i++){
						 FileVo vo = new FileVo();
						 vo.setFileName(fileName[i]);
						 vo.setFilePath(filePath[i]);
						 fileList.add(vo);
					 }
					 this.getRequest().setAttribute("fileList",fileList); //文件对象
				}
				this.getRequest().setAttribute("work", work);
				this.getRequest().setAttribute("admin", getLoginAdmin()); //获取登录账户);
				this.pageUrl = "/WEB-INF/template/admin/work_editworkCheck.ftl";
				return PAGEURL;
			}
			
		 
  	/**
       * 删除我的工作
       * @return
       
      public String delete(){
      	if(StringUtils.isEmpty(id)){
      		return ajax(Status.error, "需要删除的信息不存在!");
      	}
      	Work work = workService.get(id);
      	if(work == null){
      		return ajax(Status.error, "需要删除的信息不存在!");
      	}
      	try{
      		workService.delete(id);
      		this.logInfo = "删除我的工作：" + work.getWorkName();
      		return ajax(Status.success, "删除成功!");
      	}catch(Exception e){
      		return ajax(Status.error, "流程被引用，不能删除!");
      	}
      	
      }
      */
      
      
      	/**
		 * 获取工作流表单的联动数据
		 * @return
		 */
		public String getAjaxWorkFlowDataByWorkFlowId(){	   
			return ajax(workService.getAjaxWorkFlowDataByWorkFlowId(flowTypeId));	
		}
	
	
	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public String getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(String flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public ApprovalRecord getRecord() {
		return record;
	}

	public void setRecord(ApprovalRecord record) {
		this.record = record;
	}

	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	public String[] getFileName() {
		return fileName;
	}

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getContractCategoryId() {
		return contractCategoryId;
	}

	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}

	public Project4workflow getWorkFlowVo() {
		return workFlowVo;
	}
	public void setWorkFlowVo(Project4workflow workFlowVo) {
		this.workFlowVo = workFlowVo;
	}
	
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}