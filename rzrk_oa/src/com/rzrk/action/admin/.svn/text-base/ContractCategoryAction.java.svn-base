/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rzrk.common.CheckInfo;
import com.rzrk.common.Returns.Return3;
import com.rzrk.common.contract.ContractParseManager;
import com.rzrk.common.contract.ContractParser;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractCategoryLog;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Role;
import com.rzrk.entity.RootContractCategory;
import com.rzrk.entity.RootContractCategory.ROOT_CATEGORY_TYPE;
import com.rzrk.entity.ScriptLibrary;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractCategoryLogService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.RoleService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.service.ScriptLibraryService;
import com.rzrk.service.WorkFlowFormService;
import com.rzrk.service.WorkFlowService;
import com.rzrk.util.FreemarkerUtils;
import com.rzrk.util.JsUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.NumberUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.contract.ContentProviderField;
import com.rzrk.vo.contract.ContentProviderTable;
import com.rzrk.vo.contract.Definition;
import com.rzrk.vo.contract.Field;

/**
 * 后台Action类 - 文章
 */

@ParentPackage("admin")
public class ContractCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;
	private ContractCategory contractCategory;

	@Resource(name = "contractCategoryServiceImpl")
	private ContractCategoryService contractCategoryService;
	
	@Resource(name = "rootContractCategoryServiceImpl")
	private RootContractCategoryService rootContractCategoryService;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "deparmentServiceImpl")
	private DeparmentService deparmentService;
	
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	@Resource
	private ContractCategoryLogService contractCategoryLogService;
	
	@Resource
	WorkFlowFormService workFlowFormService;
	@Resource
	ScriptLibraryService scriptLibraryService;
	
	/**
	 * 工作流程servcie
	 */
	@Resource
	private WorkFlowService  workFlowService;
	
	
	private List<RootContractCategory> rootContractCategoryList;
	
	private List<Admin> viewerAdminList = new ArrayList<Admin>();
	
	 private List<Deparment> deparmentList = new ArrayList<Deparment>();//部门集合
	 private List<ScriptLibrary> scriptLibraryList = new ArrayList<ScriptLibrary>();//脚本库集合
	 
	 private List<ContractCategory> contractCategoryList = new ArrayList<ContractCategory>();//子表集合
	
	private String fieldName;
	private String expressionArgJson;
	
	public List<Admin> getViewerAdminList() {
		return viewerAdminList;
	}

	public void setViewerAdminList(List<Admin> viewerAdminList) {
		this.viewerAdminList = viewerAdminList;
	}

	public List<RootContractCategory> getRootContractCategoryList() {
		return rootContractCategoryList;
	}

	public void setRootContractCategoryList(
			List<RootContractCategory> rootContractCategoryList) {
		this.rootContractCategoryList = rootContractCategoryList;
	}

	public ContractCategory getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(ContractCategory contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getExpressionArgJson() {
		return expressionArgJson;
	}

	public void setExpressionArgJson(String expressionArgJson) {
		this.expressionArgJson = expressionArgJson;
	}

	public List<ContractCategory> getContractCategoryList() {
		return contractCategoryList;
	}

	public void setContractCategoryList(List<ContractCategory> contractCategoryList) {
		this.contractCategoryList = contractCategoryList;
	}
	public List<ScriptLibrary> getScriptLibraryList() {
		return scriptLibraryList;
	}

	public void setScriptLibraryList(List<ScriptLibrary> scriptLibraryList) {
		this.scriptLibraryList = scriptLibraryList;
	}

	//TODO 待优化
	private JSONArray getChildJsonBuiltIn(Deparment parent){
		JSONArray jsonArray = new JSONArray();
		if(parent!=null){
			for(Deparment dep : parent.getChildDeparments()){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", dep.getId());
				jsonObj.put("text", dep.getName());
				jsonObj.put("children", getChildJsonBuiltIn(dep));
				jsonArray.add(jsonObj);
			}
		}
		return jsonArray;
	}
	private JSONArray getAjaxTree4BuiltIn() {
		JSONArray jsonRootArray = new JSONArray();
		List<Deparment> deparmentList = deparmentService.getRootDeparment();
	    if(deparmentList != null){
	    	for(Deparment root : deparmentList){
	    		if(root!=null){
	    			JSONObject jsonRoot = new JSONObject();
	    			jsonRoot.put("id", root.getId());
	    			jsonRoot.put("text", root.getName());
	    			jsonRoot.put("children", getChildJsonBuiltIn(root));
	    			jsonRootArray.add(jsonRoot);
	    		}
	    	}
	    }
		
		return jsonRootArray;
	}
	/**
	* 获取二级分类列内容提供者的下拉待选项
	*/
	private List<ContentProviderTable> getContentProviders(){
		List<ContentProviderTable> tableList = new ArrayList<ContentProviderTable>();
		List<ContractCategory> contractCategoryList = contractCategoryService.getContentProviders();
		for(ContractCategory contractCategory : contractCategoryList){
			ContentProviderTable table = new ContentProviderTable();
			table.setId(contractCategory.getId());
			table.setName(contractCategory.getName());
			for(Field field : contractCategory.getDefinitionObj().getContentProviders()){
				ContentProviderField f = new ContentProviderField();
				f.setId(field.getName());
				f.setName(field.getName());
				table.getFields().add(f);
			}
			tableList.add(table);
		}
		return tableList;
	}
	/**
	供父子表关联操作
	*/
	private JSONArray getContractCategoryOptionList(){
		JSONArray arr = new JSONArray();
		List<ContractCategory> contractCategoryOptionList= contractCategoryService.getAllList();
		for(ContractCategory contractCategory : contractCategoryOptionList){
			JSONObject obj = new JSONObject();
			obj.put("id", contractCategory.getId());
			obj.put("name", contractCategory.getName());
			arr.add(obj);
		}
		return arr;
	}
	
	// 添加
	public String add() {
		JSONArray contractCategoryOptionList = getContractCategoryOptionList();
		getRequest().setAttribute("contractCategoryOptionListJson", JsonUtil.toJson(contractCategoryOptionList));
		String parserNames = contractCategoryService.getContractFieldParserNames();
		rootContractCategoryList = rootContractCategoryService.getAllList();
		getRequest().setAttribute("parserNames", parserNames);
		getRequest().setAttribute("adminList", adminService.getAllList());
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
		List<Admin> adminList = adminService.getAllList();
        JSONArray adminArr = new JSONArray();
		for(Admin admin : adminList){
			JSONObject adminObj = new JSONObject();
			adminObj.put("id", admin.getId());
			adminObj.put("name", admin.getName());
			adminArr.add(adminObj);
		}
		getRequest().setAttribute("admin4Easyui", adminArr);
		getRequest().setAttribute("workFlowList", workFlowService.getWorkFlowList());
		getRequest().setAttribute("jsonObj", jsonObj);
		getRequest().setAttribute("deparmentList", deparmentList);
		JSONArray depTree4Easyui = getAjaxTree4BuiltIn();
		getRequest().setAttribute("depTree4Easyui", depTree4Easyui);
		List<ContentProviderTable> contentProviderList = getContentProviders();
		getRequest().setAttribute("contentProviderList", JsonUtil.toJson(contentProviderList));
		List<ScriptLibrary> scriptLibraryList = scriptLibraryService.getAllList();
		JSONArray scriptLibraryOptionListArr = new JSONArray();
		for(ScriptLibrary scriptLibrary : scriptLibraryList){
			JSONObject scriptLibraryOption = new JSONObject();
			scriptLibraryOption.put("id", scriptLibrary.getId());
			scriptLibraryOption.put("name", scriptLibrary.getName());
			scriptLibraryOptionListArr.add(scriptLibraryOption);
		}
		getRequest().setAttribute("scriptLibraryOptionListJson", JsonUtil.toJson(scriptLibraryOptionListArr));
	    getRequest().setAttribute("deparmentList", deparmentService.getDeparmnetList());//获取所有部门json数据
	    getRequest().setAttribute("deparmentAdminList", deparmentService.getAllDeparemntAdmin());//获取所有部门下成员json数据
		return INPUT;
	}

	// 编辑
	public String edit() throws UnsupportedEncodingException {
		JSONArray contractCategoryOptionList = getContractCategoryOptionList();
		getRequest().setAttribute("contractCategoryOptionListJson", JsonUtil.toJson(contractCategoryOptionList));
		contractCategory = contractCategoryService.get(id);
		String encodeScript = Base64.encodeBase64URLSafeString(StringUtils.defaultIfEmpty(contractCategory.getScript(),"").getBytes("utf-8") );
		getRequest().setAttribute("encodeScript", encodeScript);
		String parserNames = contractCategoryService.getContractFieldParserNames();
		getRequest().setAttribute("parserNames", parserNames);
		getRequest().setAttribute("adminList", adminService.getAllList());
		rootContractCategoryList = rootContractCategoryService.getAllList();
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
		List<Admin> adminList = adminService.getAllList();
        JSONArray adminArr = new JSONArray();
		for(Admin admin : adminList){
			JSONObject adminObj = new JSONObject();
			adminObj.put("id", admin.getId());
			adminObj.put("name", admin.getName());
			adminArr.add(adminObj);
		}
		getRequest().setAttribute("admin4Easyui", adminArr);
		getRequest().setAttribute("workFlowList", workFlowService.getWorkFlowList());
		getRequest().setAttribute("jsonObj", jsonObj);
		getRequest().setAttribute("deparmentList", deparmentList);
		List<ContractCategoryLog> clogs = contractCategoryLogService.getByContractCategoryId(id);
		getRequest().setAttribute("clogs", clogs);
		JSONArray depTree4Easyui = getAjaxTree4BuiltIn();
		getRequest().setAttribute("depTree4Easyui", depTree4Easyui);
		List<ContentProviderTable> contentProviderList = getContentProviders();
		getRequest().setAttribute("contentProviderList", JsonUtil.toJson(contentProviderList));
		List<ScriptLibrary> scriptLibraryList = scriptLibraryService.getAllList();
		JSONArray scriptLibraryOptionListArr = new JSONArray();
		for(ScriptLibrary scriptLibrary : scriptLibraryList){
			JSONObject scriptLibraryOption = new JSONObject();
			scriptLibraryOption.put("id", scriptLibrary.getId());
			scriptLibraryOption.put("name", scriptLibrary.getName());
			scriptLibraryOptionListArr.add(scriptLibraryOption);
		}
		getRequest().setAttribute("scriptLibraryOptionListJson", JsonUtil.toJson(scriptLibraryOptionListArr));
	    getRequest().setAttribute("deparmentListJson", deparmentService.getDeparmnetList());//获取所有部门json数据
	    getRequest().setAttribute("deparmentAdminListJson", deparmentService.getAllDeparemntAdmin());//获取所有部门下成员json数据
		return INPUT;
	}


	// 列表
	public String list() {
		Admin admin = getLoginAdmin();
		if(admin.getRoleSet().contains(roleService.getSuperRole())){
			getRequest().setAttribute("superRole", true);
		}else{
			getRequest().setAttribute("superRole", false);
		}
		//pager = articleService.findPager(pager);
		return LIST;
	}
	
	// 是否已存在 ajax验证
	public String checkContractCategoryName() {
		String name = contractCategory.getName();
		if (contractCategoryService.isExistByCategoryname(name)) {
			return ajax("false");
		} else {
			return ajax("true");
		}
	}
	public String ajaxGetList(){
		processAjaxPagerRequestParameter();
		
		String orderBy = getRequest().getParameter("sort");// 排序字段
		String orderStr = getRequest().getParameter("order");// 排序方式
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ContractCategory.class);
		pager.setOrderBy("");
//		pager = contractCategoryService.findPager(pager,Restrictions.eq("recyle", false));
		
		if(StringUtils.isNotEmpty(orderBy)){
			if(StringUtils.equals(orderBy, "rootContractCategoryName")){
				detachedCriteria.createAlias("rootContractCategory", "r");
				orderBy = "r.name";
			}
	    	if (orderStr.equals("asc")){
	    		detachedCriteria.addOrder(Order.asc(orderBy));
	    	}else{
	    		detachedCriteria.addOrder(Order.desc(orderBy));
	    	}
		}
		pager = contractCategoryService.findPager(pager,detachedCriteria);
		List<ContractCategory> ccList = (List<ContractCategory>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < ccList.size(); i++ ){
			ContractCategory temp = ccList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("name", temp.getName());
	        map.put("isUrlView", temp.getIsUrlView());
	        map.put("fields",StringUtils.join(temp.getDefinitionObj().getTitles(),","));
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd"));
	        RootContractCategory catrgory =  temp.getRootContractCategory();
	        if(catrgory != null){
	        	if(catrgory.getName() != null){
	        		map.put("rootContractCategoryName", temp.getRootContractCategory().getName());
	        	}
	        }else{
	        	map.put("rootContractCategoryName", "");
	        }
	    	//TODO 有N+1问题
	        List<WorkFlowForm> formlist = workFlowFormService.getByContractCategoryId(temp.getId());
	        
	        map.put("isApprovalNeeded", formlist.isEmpty()? "否" : "是" );
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
		
	}
	
//	public String ajaxGetAllList(){
//		List<ContractCategory> ccList = contractCategoryService.getAllList();
//		JSONArray jsonObj = new JSONArray();
//		for(int i = 0; i < ccList.size(); i++ ){
//			ContractCategory temp = ccList.get(i);
//	        Map<String,Object> map = new HashMap<String,Object>(); 
//	        map.put("id", temp.getId()); 
//	        map.put("name", temp.getName());
//	        jsonObj.add(map);
//		}
//		return ajax(jsonObj);
//		
//	}

	private String hasRepeat(String[] args){  
	    Set<Object> tempSet = new HashSet<Object>();  
	    for (int i = 0; i < args.length; i++) {  
	    	if(tempSet.contains(args[i])){
	    		return args[i];
	    	}else{
		        tempSet.add(args[i]);  
	    	}
	    }
	    return null;
	}
	private CheckInfo checkContract(){
		CheckInfo parseInfo = new CheckInfo();
all:	do{
			String fields = StringUtils.join( contractCategory.getDefinitionObj().getTitles());
			if(StringUtils.isEmpty(fields)){
				parseInfo.addError("没有选定的字段");
				break all;
			}
			
			String[] fieldArr = fields.split(",");
			String repeatStr = hasRepeat(fieldArr);
			if(repeatStr!=null){
				parseInfo.addError("字段重复:"+repeatStr);
				break all;
			};
			
			String parseNames = contractCategoryService.getContractFieldParserNames();
			if(!StringUtils.isEmpty(parseNames)){
				String[] parseNameArr = parseNames.split(",");
				if(ArrayUtils.contains(parseNameArr, fieldArr[0])){
					parseInfo.addError("主键不能和内置字段同名："+fieldArr[0]);
				}
			}
		}while(false);
		return parseInfo;
	}
	
	@InputConfig(resultName = "error")
	public String save() {
		if(StringUtils.isBlank(contractCategory.getWorkFlowId())){
			contractCategory.setWorkFlowId(null);
		}
		if(contractCategory.getParent()!=null  && StringUtils.isBlank(contractCategory.getParent().getId())){
			contractCategory.setParent(null);
		}
		CheckInfo parseInfo = checkContract();
		if(parseInfo.isError()){
			return ajax(Status.error, parseInfo.errorMessage());
		}
		
		//检测流程完整性
		if(WorkFlowContants.CHECK.intValue() == contractCategory.getApprovalNeeded()){//需要审核
			if( StringUtils.isNotEmpty(contractCategory.getWorkFlowId())){
				WorkFlow tempFlow = workFlowService.get(contractCategory.getWorkFlowId());
				if(tempFlow != null ){
					if(!(tempFlow.getWorkFlowPointSet() != null && tempFlow.getWorkFlowPointSet().size() > 0)){
						return ajax(Status.error, "审批流程未定义节点，请定义节点!");
					}
				}else{
					return ajax(Status.error, "二级分类所选流程不存在,请选择其他流程!");
				}
				
			}else{
				return ajax(Status.error, "请创建审批流程!");
			}
		}
		try {
//			Assert.notEmpty(deparmentList, "部门不能为空");
			contractCategory.setViewPowerAdminList(new HashSet<Admin>(viewerAdminList));
			contractCategory.setDeparmentSet(new HashSet<Deparment>(deparmentList));
			contractCategory.setChildSet(new HashSet<ContractCategory>(contractCategoryList));
			contractCategory.setScriptLibrarySet(new HashSet<ScriptLibrary>(scriptLibraryList));
			contractCategoryService.saveWithCheck(contractCategory);
			logInfo = "添加类型: " + contractCategory.getName();
			redirectUrl = "contract_category!list.action";
			Role role = roleService.getSuperRole();
			List<String> tempCCList = role.getCcList();
			tempCCList.add(contractCategory.getId());
			role.setCcList(tempCCList);
			roleService.update(role);
			return ajax(Status.success, "添加类型成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo = "添加类型: " + contractCategory.getName()+"失败:"+e.getMessage();
			redirectUrl = "contract_category!list.action";
			return ajax(Status.error, e.getMessage());
		}
	}

	

	// 更新
	@Validations(
			requiredStrings = { 
					@RequiredStringValidator(fieldName = "contractCategory.name", message = "类型不允许为空!"),
					@RequiredStringValidator(fieldName = "contractCategory.fields", message = "类型字段不允许为空!")
			}
		)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		if(StringUtils.isBlank(contractCategory.getWorkFlowId())){
			contractCategory.setWorkFlowId(null);
		}
		if(contractCategory.getParent()!=null  && StringUtils.isBlank(contractCategory.getParent().getId())){
			contractCategory.setParent(null);
		}
		Admin admin = getLoginAdmin();
		CheckInfo parseInfo = checkContract();
		if(parseInfo.isError()){
			return ajax(Status.error, parseInfo.errorMessage());
		}
		//检测流程完整性
		if(WorkFlowContants.CHECK.intValue() == contractCategory.getApprovalNeeded()){//需要审核
			if(StringUtils.isNotEmpty(contractCategory.getWorkFlowId())){
				WorkFlow tempFlow = workFlowService.load(contractCategory.getWorkFlowId());
				if(tempFlow != null){
					if(!(tempFlow.getWorkFlowPointSet() != null && tempFlow.getWorkFlowPointSet().size() > 0)){
						return ajax(Status.error, "审批流程未定义节点，请定义节点!");
					}
				}else{
					return ajax(Status.error, "二级分类所选流程不存在,请选择其他流程!");
				}
				
			}else{
				return ajax(Status.error, "请创建审批流程!");
			}
		}
		ContractCategory persistent = contractCategoryService.get(id);
		ArrayList<String> sb = new ArrayList<String>();
		Map<String, Object> defChangeMap =new HashMap<String, Object>();
		if(persistent.getDefinitionObj().isChange(contractCategory.getDefinitionObj(),defChangeMap)){
			sb.add("修改列定义:'"+defChangeMap.get("self")+"' --> '"+defChangeMap.get("other")+"'");
		};		
//		if(!ObjectUtils.equals(persistent.getTotals(), contractCategory.getTotals())){
//			sb.add("修改统计列:'"+persistent.getTotals()+"' --> '"+contractCategory.getTotals()+"'");
//		};		
		if(!ObjectUtils.equals(persistent.getIsView(), contractCategory.getIsView())){
			if("1".equals(contractCategory.getIsView())){
				sb.add("增加允许部门人员查看");
			}else{
				sb.add("删除允许部门人员查看");
			}
		};		
		if(!ObjectUtils.equals(persistent.getIsSubDepView(), contractCategory.getIsSubDepView())){
			if("1".equals(contractCategory.getIsSubDepView())){
				sb.add("增加允许子部门人员查看");
			}else{
				sb.add("删除允许子部门人员查看");
			}
		};		
		if(!ObjectUtils.equals(persistent.getIsSuperiorView(), contractCategory.getIsSuperiorView())){
			if("1".equals(contractCategory.getIsSuperiorView())){
				sb.add("增加允许上级部门领导查看");
			}else{
				sb.add("删除允许上级部门领导查看");
			}
		};		
		if(!ObjectUtils.equals(persistent.getApprovalNeeded(), contractCategory.getApprovalNeeded())){
			if(contractCategory.getApprovalNeeded()==1){
				sb.add("修改为需要审核");
			}else{
				sb.add("修改为不需要审核");
			}
		};
		if(!ObjectUtils.equals(persistent.getRootContractCategory().getId(), contractCategory.getRootContractCategory().getId())){
			sb.add("更换一级分类:'"+persistent.getRootContractCategory().getName()+"' --> '"+rootContractCategoryService.get(contractCategory.getRootContractCategory().getId()).getName()+"'");
		};
		Collection<String> fromDepids = CollectionUtils.collect(persistent.getDeparmentSet(), new Transformer() {
			@Override
			public Object transform(Object arg0) {
				Deparment dep = (Deparment)arg0;
				return dep.getId();
			}
		});
		Collection<String> toDepids = CollectionUtils.collect(deparmentList, new Transformer() {
			@Override
			public Object transform(Object arg0) {
				Deparment dep = (Deparment)arg0;
				return dep.getId();
			}
		});
		if(!CollectionUtils.isEqualCollection(fromDepids, toDepids)){
			Collection<String> fromDepnames = CollectionUtils.collect(persistent.getDeparmentSet(), new Transformer() {
				@Override
				public Object transform(Object arg0) {
					Deparment dep = (Deparment)arg0;
					return dep.getName();
				}
			});
			Collection<String> toDepnames = CollectionUtils.collect(deparmentList, new Transformer() {
				@Override
				public Object transform(Object arg0) {
					Deparment dep = (Deparment)arg0;
					return deparmentService.get(dep.getId()).getName();
				}
			});
			sb.add("更换部门:'"+StringUtils.join(fromDepnames,",")+"' --> '"+StringUtils.join(toDepnames,",")+"'");
		}

		
		if(sb.size()>0){
			ContractCategoryLog clog = new ContractCategoryLog();
			clog.setOperator(admin);
			clog.setContractCategory(persistent);
			clog.setContent(StringUtils.join(sb, "<br/>"));
			contractCategoryLogService.saveOrUpdate(clog);
			persistent.getContractCategoryLogSet().add(clog);
		}
		BeanUtils.copyProperties(contractCategory, persistent, new String[] {"id","createDate", "modifyDate", "deparmentSet", "queryHistorySet", "viewPowerAdminList","contractCategoryLogSet","contractLogSet","childSet","scriptLibrarySet"});
		persistent.setViewPowerAdminList(new HashSet<Admin>(viewerAdminList));
	    persistent.setDeparmentSet(new HashSet<Deparment>(deparmentList));
	    persistent.setScriptLibrarySet(new HashSet<ScriptLibrary>(scriptLibraryList));
//		contractCategoryService.saveOrUpdate(persistent);
		contractCategoryService.saveOrUpdate(persistent,contractCategoryList);
		logInfo = "修改类型: " + contractCategory.getName();
		redirectUrl = "contract_category!list.action";
		return ajax(Status.success, "修改类型成功!");
	}
	
/*	//组装表格数据
	public void createTableData(ContractCategory contractCategory){
	//	String categoryLine = this.getRequest().getParameter("categoryLine");//表格行
		String categoryColumn = this.getRequest().getParameter("categoryColumn");//表格列
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(categoryLine)){
			resultMap.put("categoryLine", categoryLine);
		}else{
			resultMap.put("categoryLine", "");
		}
		if(StringUtils.isNotEmpty(categoryColumn)){
			resultMap.put("categoryColumn", categoryColumn);
		}else{
			resultMap.put("categoryColumn", "");
		}
		contractCategory.setFormTemplate(JsonUtil.toJson(resultMap));
		
	}*/

	// 删除
	public String delete() throws Exception {
		
		Return3<Boolean, String, String> res =  contractCategoryService.deletesWithCheck(ids);
		if(!res.getR1()){
			return ajax(Status.error, res.getR2());
		}else{
			logInfo = res.getR3();
			return ajax(Status.success, "删除成功!");
		}
//		StringBuffer logInfoStringBuffer = new StringBuffer("删除类型");
//		for (String id : ids) {
//			ContractCategory cc = contractCategoryService.get(id);
//			if (!contractCategoryService.deleteWithCheck(id)) {
//				return ajax(Status.error, "数据被引用!");
//			}
//			
//			List<WorkFlowForm> workFlowFormList =  workFlowFormService.find(DetachedCriteria.forClass(WorkFlowForm.class).add(Restrictions.eq("contractCategoryId", id)));
//			if(workFlowFormList.size()>0){
//				return ajax(Status.error, "被流程表单["+workFlowFormList.get(0).getFormName()+"]引用!");
//			}
//			contractCategoryService.deleteFromId(id);
//			logInfoStringBuffer.append(" ").append(cc.getName());
//
//		}
//		logInfo = logInfoStringBuffer.toString();
//		return ajax(Status.success, "删除成功!");
	}
	
	public String responseHSSFWorkbook(HSSFWorkbook wb,String fileName) throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.reset();  
        response.setContentType("application/vnd.ms-excel;charset=utf-8");  
        response.setHeader("Content-Disposition", "attachment;filename=" 
        		+ URLEncoder.encode(fileName, "UTF-8"));
		wb.write(response.getOutputStream());
		return NONE;
	}
	
	public String downloadTemp() throws Exception{
		HSSFWorkbook wb = contractCategoryService.getDownloadTemp(id,getLoginAdmin());
        return responseHSSFWorkbook(wb,wb.getSheetAt(0).getSheetName()+".xls");
	}

	public List<Deparment> getDeparmentList() {
		return deparmentList;
	}

	public void setDeparmentList(List<Deparment> deparmentList) {
		this.deparmentList = deparmentList;
	}
	@Resource
	ContractParseManager contractParseManager;
	public String initDefinition(){
		List<ContractCategory> cclist = contractCategoryService.getAllList();
		for(ContractCategory cc : cclist){
			if(StringUtils.isEmpty(cc.getDefinition())){
				Definition definition = new Definition();
				String[] fields = cc.getFields().split(",");
				String[] totoals = null;
				if(StringUtils.isNotEmpty(cc.getTotals())){
					 totoals = cc.getTotals().split(",");
				}else{
					 totoals = new String[0];
				}
				
				for(String name : fields){
					Field field = new Field();
					field.setName(name);
					if(ArrayUtils.contains(totoals, name)){
						field.setTotal(true);
					}
					ContractParser contractParser  = contractParseManager.getContractParser(name);
					if(contractParser !=null){
						field.setBuilt(true);
						field.setBuiltsign(contractParser.getName());
					}else{
						field.setType(Field.TYPE_TEXT);
					}
					definition.add(field);
				}
				cc.setDefinitionObj(definition);
				contractCategoryService.update(cc);
			}
		}
		
		
		return ajax(true);
	}
	
	public String expressionScript(){
		try {
			List<String> expressionScriptList = contractCategoryService.getExpressionScriptList(id);
			return ajax(StringUtils.join(expressionScriptList,"\n"));
		} catch (Exception e) {
			e.printStackTrace();
			return ajax("");
		}

	}
	public String expressionCalc(){
		String res = "";
		try {
			ContractCategory  contractCategory = contractCategoryService.get(id);
			Map<String,String> expressionArg = JsonUtil.toObject(expressionArgJson, Map.class);
			Definition expDef = contractCategory.getDefinitionObj();
			List<String> expressionScriptList = new ArrayList<String>();
			Field field = expDef.getField(fieldName);
			if(field!=null && !field.isBuilt() && StringUtils.equals(field.getType(),"表达式")&& StringUtils.isNotBlank(field.getExpression())){
				final String showFieldName = field.getName();
				final LinkedHashSet<String> inputFieldNameList = new LinkedHashSet<String>();
				String expStr = field.getExpression();
				Pattern pattern=Pattern.compile("\\$\\((.+?)\\)");
				Matcher matcher=pattern.matcher(expStr);
			    StringBuffer sb=new StringBuffer();
			    boolean find=false;
			    while(find=matcher.find()){
			    	String name = matcher.group(1);
			    	inputFieldNameList.add(name);
			    	String placeholder = StringUtils.defaultIfBlank(expressionArg.get(name),"0");
			    	matcher.appendReplacement(sb,placeholder);
			    }
			    matcher.appendTail(sb);
			    String expStrTarget = sb.toString();
			    Number num = JsUtil.getNumber(expStrTarget);
			    BigDecimal gd = BigDecimal.valueOf(num.doubleValue());
				if(gd.doubleValue() == gd.longValue()){
					res = gd.toBigInteger().toString();
				}else{
					res = BigDecimal.valueOf(num.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
				}
				return ajax(Status.success,res);
			}else{
				return ajax(Status.error,"不存在表达式");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajax(Status.error,"表达式错误："+e.getMessage());
		}
	}
	
	public String getPermissionField(){
		try {
			contractCategory = contractCategoryService.get(id);
			List<Field> fieldList = contractCategoryService.getPermissionField(contractCategory, getLoginAdmin());
			JSONArray array = new JSONArray();
			Field primaryField = contractCategory.getDefinitionObj().getPrimary();
			for(Field field : fieldList){
				if(!StringUtils.equals(primaryField.getName(), field.getName())){
					JSONObject entity = new JSONObject();
					entity.put("id", field.getName());
					entity.put("text", field.getName());
					array.add(entity);
				}
			}
			
			return ajax(Status.success,"成功",array);
		} catch (Exception e) {
			e.printStackTrace();
			return ajax(false);
		}

	}
	
	public String getInitScripts(){
		ContractCategory contractCategory = contractCategoryService.get(id);
		StringBuffer sb = new StringBuffer("");
		for(ScriptLibrary scriptLibrary: contractCategory.getScriptLibrarySet()){
			sb.append(scriptLibrary.getScript()).append("\r\n");
		}
		sb.append(contractCategory.getScript());
		return ajax(sb.toString());
	}

	
}