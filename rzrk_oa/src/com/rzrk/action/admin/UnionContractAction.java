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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
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
import com.rzrk.entity.UnionContractCategory;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.UnionContractService;
import com.rzrk.service.UnionContractCategoryService;
import com.rzrk.service.WorkService;
import com.rzrk.util.HttpClientUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.FileVo;
import com.rzrk.vo.UserInModelPo;
import com.rzrk.vo.UserLoginfPO;
import com.rzrk.vo.querytree.Cond;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.unioncontract.Definition;
import com.rzrk.vo.unioncontract.Field;

/**
 * 后台Action类 - 文章
 */

@ParentPackage("admin")
public class UnionContractAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

	@Resource
	private UnionContractService unionContractService;

	@Resource
	private UnionContractCategoryService unionContractCategoryService;
	
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
	private String unionContractCategoryId;
	private String contentProviderField;
	
	
	public String getContractEntity() {
		return contractEntity;
	}

	public void setContractEntity(String contractEntity) {
		this.contractEntity = contractEntity;
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

	public String getUnionContractCategoryId() {
		return unionContractCategoryId;
	}

	public void setUnionContractCategoryId(String unionContractCategoryId) {
		this.unionContractCategoryId = unionContractCategoryId;
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


/*
	// 查看
		public String view() throws Exception {
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
			return VIEW;
		}
*/

	// 列表
	public String list() {
	
		UnionContractCategory  unionContractCategory = unionContractCategoryService.get(unionContractCategoryId);
		Definition definition = unionContractCategory.getDefinitionObj();
		List<Field> fieldList = unionContractCategoryService.getPermissionField(unionContractCategory, getLoginAdmin());
		
		ArrayList<String> titleArr = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(fieldList)){
			getRequest().setAttribute("fieldList", fieldList);
			getRequest().setAttribute("definition", JsonUtil.toJson(fieldList));
			getRequest().setAttribute("queryOption", new Definition(fieldList).getTableFileds());
		}else{
			getRequest().setAttribute("fieldList", new ArrayList<Field>());
			getRequest().setAttribute("definition", "[]");
			getRequest().setAttribute("queryOption", new Definition());
		}
		
		return LIST;
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
		pager = unionContractService.findPager(pager,unionContractCategoryId,node);
		List<Map<String, String>> result = (List<Map<String, String>>) pager.getResult();

		UnionContractCategory contractCategory = unionContractCategoryService.get(unionContractCategoryId);
		String[] fields = com.rzrk.util.StringUtils.fromCollection(
				CollectionUtils.collect(unionContractCategoryService.getPermissionField(contractCategory,getLoginAdmin()),new Transformer() {
					@Override
					public Object transform(Object arg0) {
						return ((Field)arg0).getShowField();
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


	

	
	public String getAjaxList(){
		 Map<String,Object> map = new HashMap<String,Object>(); 
		 processAjaxPagerRequestParameter();
		 Node node = JsonUtil.toObject(preQuery, Node.class);
		pager = unionContractService.findPager(pager,unionContractCategoryId,node);
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", pager.getResult());
//        map.put("footer", pager.getFooter());
   		return ajax(map);
		
	}

	
/*	public String getBuiltContentProviderResList(){
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
*/

	
}