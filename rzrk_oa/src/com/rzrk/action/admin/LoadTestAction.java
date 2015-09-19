/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;












import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.contants.CategoryContants;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ApprovalRecord;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Project;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.WorkService;
import com.rzrk.util.HttpClientUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.FileVo;
import com.rzrk.vo.UserInModelPo;
import com.rzrk.vo.contract.ContractEntity;
import com.rzrk.vo.contract.Field;
import com.rzrk.vo.querytree.Cond;
import com.rzrk.vo.querytree.Node;

/**
 * 后台Action类 - 文章
 */

public class LoadTestAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

	@Resource(name = "contractServiceImpl")
	private ContractService contractService;

	@Resource(name = "contractCategoryServiceImpl")
	private ContractCategoryService contractCategoryService;
	
	@Resource
	DeparmentService deparmentService;
    @Resource
	WorkService workService;
    
    /**
     * 项目service
     */
    @Resource
    private ProjectService projectService;

	private List<Admin>  adminList; //访问用户
	private String titles;
	private String[] fields;
	private String preQuery; //预先设置的查询条件
	private String  contractEntity;
	private String rowId;
	private String[] rowIds;
	private String contractCategoryId;
	private String contentProviderField;
	
	
	public String getContractEntity() {
		return contractEntity;
	}

	public void setContractEntity(String contractEntity) {
		this.contractEntity = contractEntity;
	}

	public ContractService getContractService() {
		return contractService;
	}

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}


	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getPreQuery() {
		return preQuery;
	}

	public void setPreQuery(String preQuery) {
		this.preQuery = preQuery;
	}

	
	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getContractCategoryId() {
		return contractCategoryId;
	}

	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}
	
	public String[] getRowIds() {
		return rowIds;
	}

	public void setRowIds(String[] rowIds) {
		this.rowIds = rowIds;
	}
	public String getContentProviderField() {
		return contentProviderField;
	}

	public void setContentProviderField(String contentProviderField) {
		this.contentProviderField = contentProviderField;
	}

	// 编辑
	public String add() throws Exception {
		ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
		final List<Field> fieldlist = contractCategoryService.getPermissionField(contractCategory, getLoginAdmin());
		String[] titleArr = com.rzrk.util.StringUtils.fromCollection(CollectionUtils.collect(fieldlist, new Transformer() {
				@Override
				public Object transform(Object arg0) {
					return ((Field)arg0).getName();
				}
			}));
		getRequest().setAttribute("contractCategory", contractCategory);
		getRequest().setAttribute("title0", titleArr[0]);
		getRequest().setAttribute("titles", StringUtils.join(titleArr,","));
		getRequest().setAttribute("titleArr", titleArr);
		getRequest().setAttribute("contractCategoryName", contractCategory.getName());
		getRequest().setAttribute("definition",  fieldlist);
		List<String> expressionScriptList = contractCategoryService.getExpressionScriptList(contractCategoryId);
		getRequest().setAttribute("expressionScriptList",  expressionScriptList);
		return INPUT;
	}

	// 编辑
	public String edit() throws Exception {
		if(StringUtils.isEmpty(id)){
			setId(getRowId());
		}
		ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
		final List<Field> fieldlist = contractCategoryService.getPermissionField(contractCategory, getLoginAdmin());
		String[] titleArr = com.rzrk.util.StringUtils.fromCollection(CollectionUtils.collect(fieldlist, new Transformer() {
				@Override
				public Object transform(Object arg0) {
					return ((Field)arg0).getName();
				}
			}));
		HashMap<String, String> rowData = contractService.getContractRow(rowId, contractCategoryId, titleArr);
		List<ContractField> fieldList = contractService.getContractFieldList(rowId);
		if(fieldList != null && fieldList.size() > 0){
			getRequest().setAttribute("fieldList", fieldList.get(0).getAdminList());
		}
		getRequest().setAttribute("contractCategory", contractCategory);
		getRequest().setAttribute("title0", titleArr[0]);
		getRequest().setAttribute("titles", StringUtils.join(titleArr,","));
		getRequest().setAttribute("titleArr", titleArr);
		getRequest().setAttribute("rowData", rowData);
		getRequest().setAttribute("rowDataJson", JsonUtil.toJson(rowData));
		getRequest().setAttribute("definition",  fieldlist);
		getRequest().setAttribute("contractCategoryName", contractCategory.getName());
		List<String> expressionScriptList = contractCategoryService.getExpressionScriptList(contractCategoryId);
		getRequest().setAttribute("expressionScriptList",  expressionScriptList);
		
		ContractField contractFieldPrimary = contractService.get(rowId);
		List<Work> workList = workService.getByContractFieldPrimary(contractFieldPrimary);
		List<ApprovalRecord> recordListSave = new ArrayList<ApprovalRecord>();
		List<ApprovalRecord> recordListModify = new ArrayList<ApprovalRecord>();
		List<ApprovalRecord> recordListDelete = new ArrayList<ApprovalRecord>();
		List<FileVo> fileList = new ArrayList<FileVo>();
		if(CollectionUtils.isNotEmpty(workList)){
			for(Work work : workList){
				if(StringUtils.equals(work.getIsDelete(),WorkFlowContants.UPDATE)){
					recordListModify.addAll(work.getRecord());
				}else if(StringUtils.equals(work.getIsDelete(),WorkFlowContants.DELETE)){
					recordListDelete.addAll(work.getRecord());
				}else{
					recordListSave.addAll(work.getRecord());
				}
				if(work.getFileName() != null && work.getFilePath() != null){
					String[] fileName = work.getFileName().split(",");//文件名称
					String[] filePath = work.getFilePath().split(",");//文件路径
					 for(int i = 0; i < fileName.length; i++){
						 FileVo vo = new FileVo();
						 vo.setFileName(fileName[i]);
						 vo.setFilePath(filePath[i]);
						 fileList.add(vo);
					 }
				}
			}
		}
		this.getRequest().setAttribute("recordListSave", recordListSave);
		this.getRequest().setAttribute("recordListModify", recordListModify);
		this.getRequest().setAttribute("recordListDelete", recordListDelete);
		this.getRequest().setAttribute("fileList",fileList); //文件对象
		return INPUT;
	}




	// 列表
	public String list() {
		
		if(contractCategoryService.get(contractCategoryId).getName().equals("Model用户")){
			getRequest().setAttribute("isModelUser", true);
		}
		if(!contractCategoryService.canUpdateContractRecord(contractCategoryId, getLoginAdmin())){
			return "accessDenied";
		}
		
		
		ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
		List<WorkFlowForm> workFlowFormList = contractCategoryService.getWorkFlowFormList(contractCategory);
		final List<Field> fieldlist = contractCategoryService.getPermissionField(contractCategory, getLoginAdmin());
		String[] titleArr = com.rzrk.util.StringUtils.fromCollection(CollectionUtils.collect(fieldlist, new Transformer() {
			@Override
			public Object transform(Object arg0) {
				return ((Field)arg0).getName();
			}
		}));

		String[] totalArr = contractCategory.getDefinitionObj().getTotals();
      // LinkedHashMap<String, HashMap<String, String>> ccList = contractService.getContractRows(contractCategoryId,titleArr);
//
//		JSONArray jsonArrayTitles = new JSONArray();
//		for(String item : titleArr){
//			jsonArrayTitles.add(item);
//		}

//		JSONArray jsonArrayRow = new JSONArray();
//		for(HashMap<String, String> item : ccList.values()){
//			jsonArrayRow.add(item);
//		}
		
		getRequest().setAttribute("definition", JsonUtil.toJson(contractCategory.getDefinitionObj()));
		getRequest().setAttribute("contractCategoryName", contractCategory.getName());
		getRequest().setAttribute("contractCategoryTitles", JsonUtil.toJson(titleArr));
		getRequest().setAttribute("contractCategoryTotals", JsonUtil.toJson(totalArr));
//		getRequest().setAttribute("contractCategoryRows", jsonArrayRow.toString());
		getRequest().setAttribute("titleArr", titleArr);
		getRequest().setAttribute("workFlowFormList",  workFlowFormList);
		getRequest().setAttribute("buildLevelFieldName",  "");
		for(Field field : fieldlist){
			if(field.isBuilt() && StringUtils.equals(field.getBuiltsign(),Field.BUILT_LEVEL)){
				getRequest().setAttribute("buildLevelFieldName", field.getName());
				break;
			}
		}
		getRequest().setAttribute("belongProjectOpts", "[]");
		getRequest().setAttribute("belongProjectMap", "{}");
		getRequest().setAttribute("contractCategorySpecify", "");
		
		
		
		if(StringUtils.equals(contractCategoryId, CategoryContants.REQUIREMENT_CATEGORY_ID)){
			getRequest().setAttribute("contractCategorySpecify", "REQUIREMENT_CATEGORY");
			for(Field field : fieldlist){
				List<Project> projectList = projectService.getAllList();
				JSONArray belongProjectOpts = new JSONArray();
				Map<String,String> belongProjectMap = new HashMap<String, String>();
				JSONObject jobjEmpty = new JSONObject();
				jobjEmpty.put("id", "");
				jobjEmpty.put("text", "无");
				jobjEmpty.put("name", "无");
				belongProjectOpts.add(jobjEmpty);
				belongProjectMap.put("","无");
				for(Project project : projectList){
					JSONObject jobj = new JSONObject();
					jobj.put("id", project.getId());
					jobj.put("text", project.getName());
					jobj.put("name", project.getName());
					belongProjectOpts.add(jobj);
					belongProjectMap.put(project.getId(),project.getName());
				}
				getRequest().setAttribute("belongProjectOpts", JsonUtil.toJson(belongProjectOpts));
				getRequest().setAttribute("belongProjectMap", JsonUtil.toJson(belongProjectMap));
			}
		}
		
		
		
		return LIST;
	}
	
	public String delete(){
		StringBuffer logInfoStringBuffer = new StringBuffer("删除内容:");
		/*if(!contractCategoryService.canUpdateContractRecord(contractCategoryId, getLoginAdmin())){
			return ajax(Status.error, "你无权修改数据!");
		}*/
		ContractCategory contractCategory = null;//二级分类
	
		String fieldList = "";
		try {
			contractCategory = contractCategoryService.get(contractCategoryId);//获取二级分类
			logInfoStringBuffer.append(contractCategory.getName());
			if(contractCategory != null){
				if(contractCategory.getApprovalNeeded() == WorkFlowContants.UNCHECK){//未检查
					List<String> delNames = contractService.selectForRowName(contractCategoryId, rowIds);
					logInfoStringBuffer.append(" ").append(StringUtils.join(delNames," "));
					contractService.deleteForRowIds(contractCategoryId, rowIds);
				}else{
					Admin loginAdmin = this.getLoginAdmin();//当前用户
					fieldList = contractService.getFieldList(contractCategoryId, rowIds,loginAdmin);
				}
			}else{
				throw new Exception("不存在："+contractCategoryId);
			}
		}catch(PersonalException e){
			logInfo = "错误："+e.getMessage();
  			return ajax(Status.error,e.getMessage());
  		}catch(Exception e){	
			logInfo = "错误："+e.getMessage();
			return ajax(Status.error, e.getMessage());
		}
		logInfo = logInfoStringBuffer.toString();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", Status.success);
		map.put("message", logInfo);
		map.put("fieldId", fieldList);
		if(contractCategory != null){
			map.put("isCheck", contractCategory.getApprovalNeeded());//是否需要审核
			
		}
		return ajax(map);

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

	public String download() throws Exception{
		Node node = JsonUtil.toObject(preQuery, Node.class);
		pager.setPageSize(Integer.MAX_VALUE);
		pager = contractService.findPager(pager,contractCategoryId,node);
		List<Map<String, String>> result = (List<Map<String, String>>) pager.getResult();

		ContractCategory contractCategory = contractCategoryService.get(contractCategoryId);
		String[] fields = com.rzrk.util.StringUtils.fromCollection(
				CollectionUtils.collect(contractCategoryService.getPermissionField(contractCategory,getLoginAdmin()),new Transformer() {
					@Override
					public Object transform(Object arg0) {
						return ((Field)arg0).getName();
					}
		}));
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFFont titleFont = wb.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFont(titleFont);
		titleStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);// 设置背景色  
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFSheet sheet = wb.createSheet(contractCategory.getName());  
        HSSFRow row = sheet.createRow(0);
        int i=0;
    	for(String fieldName : fields){
            HSSFCell cell = row.createCell((short) i++);  
            cell.setCellValue(fieldName);
            cell.setCellStyle(titleStyle);
    	}
    	int j=1;
    	for(Map<String, String> entity : result){
            row = sheet.createRow(j++);
            for(int m=0; m<fields.length;m++ ){
            	String value = entity.get(fields[m]);
                HSSFCell cell = row.createCell((short)m);  
                cell.setCellValue(value);
            }
    	}
        return responseHSSFWorkbook(wb,wb.getSheetAt(0).getSheetName()+"_"+DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+".xls");
	}
	
	@InputConfig(resultName = "error")
	public String save() {
		/*if(!contractCategoryService.canUpdateContractRecord(contractCategoryId, getLoginAdmin())){
			return ajax(Status.error, "你无权修改数据!");
		}*/
		try {
			Admin loginAdmin  = this.getLoginAdmin();//当前用户
			ContractEntity<String, String> entity = JsonUtil.toObject(contractEntity, ContractEntity.class);
//			int i=0;
//			for(String title : titles.split(",")){
//				entity.put(title, fields[i++]);
//			}
		/*	if(adminList == null){
				adminList = new ArrayList<Admin>();
			}*/
			ContractCategory contractCategory = contractCategoryService.get(contractCategoryId);//获取二级分类
			String filedId = "";
			filedId = contractService.save(null,null,contractCategoryId, entity,loginAdmin);
			logInfo = "保存内容: "+contractCategory.getName()+":"+ entity.values().iterator().next();
			redirectUrl = "contract!list.action";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", Status.success);
			map.put("message", logInfo);
			map.put("fieldId", filedId);
			if(contractCategory != null){
				map.put("isCheck", contractCategory.getApprovalNeeded());//是否需要审核
			}
			//return ajax(Status.success,logInfo);
			return ajax(map);
		}  catch(PersonalException e){
			logInfo = "错误："+e.getMessage();
  			return ajax(Status.error,e.getMessage());
  		} catch (Exception e) {
			logInfo = "错误："+e.getMessage();
			redirectUrl = "contract!list.action";
			return ajax(Status.error,logInfo);
		}
	}
	
	


	@InputConfig(resultName = "error")
	public String update() throws Exception {
		/*if(!contractCategoryService.canUpdateContractRecord(contractCategoryId, getLoginAdmin())){
			return ajax(Status.error, "你无权修改数据!");
		}*/
		try {
			Admin loginAdmin  = this.getLoginAdmin();//当前用户
			ContractEntity<String, String> entity = JsonUtil.toObject(contractEntity, ContractEntity.class);
//			int i=0;
//			for(String title : titles.split(",")){
//				entity.put(title, fields[i++]);
//			}
			if(adminList == null){
				adminList = new ArrayList<Admin>();
			}
			ContractCategory contractCategory = contractCategoryService.get(contractCategoryId);//获取二级分类
			String filedId = null;
			filedId = contractService.update(null,rowId, contractCategoryId, entity,loginAdmin);
			logInfo = "修改内容: "+contractCategory.getName()+":"+ entity.values().iterator().next();
			redirectUrl = "contract!list.action";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", Status.success);
			map.put("message", logInfo);
			map.put("fieldId", filedId);
			if(contractCategory != null){
				map.put("isCheck", contractCategory.getApprovalNeeded());//是否需要审核
			}
			//return ajax(Status.success,logInfo);
			return ajax(map);
		} catch(PersonalException e){
			logInfo = "错误："+e.getMessage();
  			return ajax(Status.error,e.getMessage());
  		}catch (Exception e) {
			logInfo = "错误："+e.getMessage();
			redirectUrl = "contract!list.action";
			return ajax(Status.error,logInfo);
		}
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	

	
	public String ajaxGetList(){
		String contractCategoryId = getRequest().getParameter("contractCategoryId");// 查找关键字
		
		/**
		 * model客户端用户数据展示
		 */
		/*try{
			if(contractCategoryService.get(contractCategoryId).getName().equals("Model用户")){
				InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("modelClient.properties");
				Properties prop = new Properties();
				prop.load(inStream);
				String ip = (String) prop.getProperty("modelServiceAddress");
				String url = "http://"+ip +"/modeltest/getUsersByOA.action";
				String param = "pageNum=1&numPerPage=50&_=1439892513878";
				String responseInfo = HttpClientUtil.sendPost(url	, param);
				com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(responseInfo);
				//List<Map<String,String>> result = new ArrayList<Map<String,String>>();
				List<UserInModelPo> result = new ArrayList<UserInModelPo>();
				int total ;
				for (total = 0; total < jsonArray.size(); total++) {
					com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) jsonArray.get(total);
					com.alibaba.fastjson.JSONObject _id = (com.alibaba.fastjson.JSONObject) jsonObject.get("_id");
					UserInModelPo userModel = new UserInModelPo();
					
					String oid = (String) _id.get("$oid");userModel.setId(oid);
					String user= (String) jsonObject.get("user");userModel.setUsername(user);
					String account_type= (String) jsonObject.get("account_type");userModel.setUser_account(account_type);
					String count= ((Integer) jsonObject.get("count")).toString();userModel.setMaxCount(count);
					String etp_user= (String) jsonObject.get("etp_user");userModel.setPlatform(etp_user);
					String expiretime= ((BigDecimal) jsonObject.get("expiretime")).toString();
					String time = expiretime.indexOf(".") == -1 ? expiretime : expiretime.substring(0, expiretime.indexOf("."));
					Long timestamp = Long.parseLong(time) * 1000;
					String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(timestamp));
					userModel.setExpiretime(date);
					String interval= ((Integer) jsonObject.get("interval")).toString();userModel.setInterval(interval);
					String isactive= ((Integer) jsonObject.get("isactive")).toString();userModel.setIsActived(isactive);
					String isupload= ((Integer) jsonObject.get("isupload")).toString();userModel.setIsUpload(isupload);
					String modeltype= ((Integer) jsonObject.get("modeltype")).toString();userModel.setModelType(modeltype);
					String pass= (String) jsonObject.get("pass");userModel.setPass(pass);
					//String reguser= (String) jsonObject.get("reguser");userModel.setReguser(reguser);
					String type= ((Integer) jsonObject.get("type")).toString();userModel.setType(type);
					//String modelauth= (String) jsonObject.get("modelauth");userModel.setModelauth(modelauth);
					result.add(userModel);
				}
				
				Map<String,Object> map = new HashMap<String,Object>(); 
				//processAjaxPagerRequestParameter();
				map.put("total", total); 
				map.put("rows", result);
				return ajax(map);
			}
		}catch(Exception e){
			return ajax(Status.error,"读取模型数据错误");
		}*/
		///////////////////////////////////////////////////////////////
				
		
		String projectId = getRequest().getParameter("projectId");// 项目
//		ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
//		String[] titleArr = contractCategory.getFields().split(",");
		 Map<String,Object> map = new HashMap<String,Object>(); 
		 processAjaxPagerRequestParameter();
		 Node node = JsonUtil.toObject(preQuery, Node.class);
		 if(StringUtils.isNotEmpty(projectId)){
			Project project =  projectService.get(projectId);
			 String categoryId = CategoryContants.REQUIREMENT_CATEGORY_ID;//二级分类ID
			 ContractCategory  contractCategory = contractCategoryService.get(categoryId);
			 String primaryKey = null;//主键
			 if(contractCategory != null){
				 primaryKey = contractCategory.getDefinitionObj().getPrimary().getName();
			 }else{
				map.put("total", 0); 
			    map.put("rows",0);
			    return ajax(map);
			 }
			if(StringUtils.isNotEmpty(project.getRequestRowids())){//需求不为空
				Cond cond = new Cond();
				cond.setSearchBy(primaryKey);
				cond.setOperate(Cond.in);
				cond.setKeyword(project.getRequestRowids().trim());
				Node  tempNode = new Node();
				tempNode.setCond(cond);
				tempNode.setRelation("and");
				node.getChildren().add(tempNode);
			}else{
				map.put("total", 0); 
			    map.put("rows",0);
			    return ajax(map);
			}
			
		 }
	
		pager = contractService.findPager(pager,contractCategoryId,node);

//		LinkedHashMap<String, HashMap<String, String>> ccList = contractService.getContractFields(contractCategoryId);
//		LinkedHashMap<String, HashMap<String, String>> ccList = contractService.f
		
//		JSONArray jsonArrayRow = new JSONArray();
//		for(HashMap<String, String> item : ccList.values()){
//			jsonArrayRow.add(item);
//		}
       
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", pager.getResult());
//        map.put("footer", pager.getFooter());
   		return ajax(map);
		
	}

	
	
	public String ajaxGetListLoadTest(){
		String contractCategoryId = getRequest().getParameter("contractCategoryId");// 查找关键字
		
		/**
		 * model客户端用户数据展示
		 */
	/*	try{
			if(contractCategoryService.get(contractCategoryId).getName().equals("Model用户")){
				InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("modelClient.properties");
				Properties prop = new Properties();
				prop.load(inStream);
				String ip = (String) prop.getProperty("modelServiceAddress");
				String url = "http://"+ip +"/modeltest/getUsersByOA.action";
				String param = "pageNum=1&numPerPage=50&_=1439892513878";
				String responseInfo = HttpClientUtil.sendPost(url	, param);
				com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(responseInfo);
				//List<Map<String,String>> result = new ArrayList<Map<String,String>>();
				List<UserInModelPo> result = new ArrayList<UserInModelPo>();
				int total ;
				for (total = 0; total < jsonArray.size(); total++) {
					com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) jsonArray.get(total);
					com.alibaba.fastjson.JSONObject _id = (com.alibaba.fastjson.JSONObject) jsonObject.get("_id");
					UserInModelPo userModel = new UserInModelPo();
					
					String oid = (String) _id.get("$oid");userModel.setId(oid);
					String user= (String) jsonObject.get("user");userModel.setUsername(user);
					String account_type= (String) jsonObject.get("account_type");userModel.setUser_account(account_type);
					String count= ((Integer) jsonObject.get("count")).toString();userModel.setMaxCount(count);
					String etp_user= (String) jsonObject.get("etp_user");userModel.setPlatform(etp_user);
					String expiretime= ((BigDecimal) jsonObject.get("expiretime")).toString();
					String time = expiretime.indexOf(".") == -1 ? expiretime : expiretime.substring(0, expiretime.indexOf("."));
					Long timestamp = Long.parseLong(time) * 1000;
					String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(timestamp));
					userModel.setExpiretime(date);
					String interval= ((Integer) jsonObject.get("interval")).toString();userModel.setInterval(interval);
					String isactive= ((Integer) jsonObject.get("isactive")).toString();userModel.setIsActived(isactive);
					String isupload= ((Integer) jsonObject.get("isupload")).toString();userModel.setIsUpload(isupload);
					String modeltype= ((Integer) jsonObject.get("modeltype")).toString();userModel.setModelType(modeltype);
					String pass= (String) jsonObject.get("pass");userModel.setPass(pass);
					//String reguser= (String) jsonObject.get("reguser");userModel.setReguser(reguser);
					String type= ((Integer) jsonObject.get("type")).toString();userModel.setType(type);
					//String modelauth= (String) jsonObject.get("modelauth");userModel.setModelauth(modelauth);
					result.add(userModel);
				}
				
				Map<String,Object> map = new HashMap<String,Object>(); 
				//processAjaxPagerRequestParameter();
				map.put("total", total); 
				map.put("rows", result);
				return ajax(map);
			}
		}catch(Exception e){
			return ajax(Status.error,"读取模型数据错误");
		}*/
		///////////////////////////////////////////////////////////////
				
		
		String projectId = getRequest().getParameter("projectId");// 项目
//		ContractCategory  contractCategory = contractCategoryService.get(contractCategoryId);
//		String[] titleArr = contractCategory.getFields().split(",");
		 Map<String,Object> map = new HashMap<String,Object>(); 
		 processAjaxPagerRequestParameter();
		 Node node = JsonUtil.toObject(preQuery, Node.class);
		 if(StringUtils.isNotEmpty(projectId)){
			Project project =  projectService.get(projectId);
			 String categoryId = CategoryContants.REQUIREMENT_CATEGORY_ID;//二级分类ID
			 ContractCategory  contractCategory = contractCategoryService.get(categoryId);
			 String primaryKey = null;//主键
			 if(contractCategory != null){
				 primaryKey = contractCategory.getDefinitionObj().getPrimary().getName();
			 }else{
				map.put("total", 0); 
			    map.put("rows",0);
			    return ajax(map);
			 }
			if(StringUtils.isNotEmpty(project.getRequestRowids())){//需求不为空
				Cond cond = new Cond();
				cond.setSearchBy(primaryKey);
				cond.setOperate(Cond.in);
				cond.setKeyword(project.getRequestRowids().trim());
				Node  tempNode = new Node();
				tempNode.setCond(cond);
				tempNode.setRelation("and");
				node.getChildren().add(tempNode);
			}else{
				map.put("total", 0); 
			    map.put("rows",0);
			    return ajax(map);
			}
			
		 }
	
		pager = contractService.findPager(pager,contractCategoryId,node);

//		LinkedHashMap<String, HashMap<String, String>> ccList = contractService.getContractFields(contractCategoryId);
//		LinkedHashMap<String, HashMap<String, String>> ccList = contractService.f
		
//		JSONArray jsonArrayRow = new JSONArray();
//		for(HashMap<String, String> item : ccList.values()){
//			jsonArrayRow.add(item);
//		}
       
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", pager.getResult());
//        map.put("footer", pager.getFooter());
   		return ajax(map);
		
	}
	
	
	
/*	
	@InputConfig(resultName = "error")
	public String save() {
		contractCategoryService.save(contractCategory);
		logInfo = "添加类型: " + contractCategory.getName();
		redirectUrl = "contract_category!list.action";
		return SUCCESS;
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
		ContractCategory persistent = contractCategoryService.load(id);
		BeanUtils.copyProperties(contractCategory, persistent, new String[] {"id","createDate", "modifyDate"});
		contractCategoryService.update(persistent);
		logInfo = "修改类型: " + contractCategory.getName();
		redirectUrl = "contract_category!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除文章: ");
		for (String id : ids) {
			ContractCategory persistent = contractCategoryService.load(id);
			contractCategoryService.delete(persistent);
			logInfoStringBuffer.append(persistent.getName() + " ");
		}
		logInfo = logInfoStringBuffer.toString();
		return ajax(Status.success, "删除成功!");
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
	
*/
	
	public String getBuiltContentProviderResList(){
		List<ContractField> fieldList = contractService.getFieldListByColumn(contractCategoryId,contentProviderField );
		LinkedHashSet<String> resSet = new LinkedHashSet<String>();
		for(ContractField field : fieldList){
			resSet.add(field.getValue());
		}
		
		ArrayList<Map<String, String>> entityList = new ArrayList<Map<String,String>>();
		for(String res : resSet){
			Map<String,String> entity = new HashMap<String, String>();
			entity.put("id", res);
			entity.put("text", res);
			entity.put("name", res);
			entityList.add(entity);
		}
		return ajax(entityList);
	}
	
	String projectId;
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String joinProject(){
		contractService.joinProject(rowId, projectId,getLoginAdmin());
		return ajax(true);
	}
	
	//http  post
	 public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
}