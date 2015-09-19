/**
 * Project Name: Unicorn Online Shopping Center
 * Confidential and Proprietary
 * Copyright (C) 2011 By
 * Unicorn Development Company
 * All Rights Reserved
 */

package com.rzrk.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.annotation.Resource;










import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.common.CheckInfo;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ContractService;

/**
 * 文件上传
 * 
 * @version 1.0
 * @author Leo
 * @since Aug 16, 2012
 */
public class ContractUploadAction extends BaseAdminAction {

	private static final long serialVersionUID = -4041917848708433121L;
	private File updateContract;
	private String updateContractFileName;
	
	@Resource(name="contractServiceImpl")
	ContractService contractService;
	
	@Resource(name = "contractCategoryServiceImpl")
	private ContractCategoryService contractCategoryService;
	
	private String contractCategoryId;
	
	
	
	
	public String getContractCategoryId() {
		return contractCategoryId;
	}

	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}

	public File getUpdateContract() {
		return updateContract;
	}

	public void setUpdateContract(File updateContract) {
		this.updateContract = updateContract;
	}

	public ContractService getContractService() {
		return contractService;
	}

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	
	public String getUpdateContractFileName() {
		return updateContractFileName;
	}

	public void setUpdateContractFileName(String updateContractFileName) {
		this.updateContractFileName = updateContractFileName;
	}

	public String updateContract(){
	/*	if(!contractCategoryService.canUpdateContractRecord(contractCategoryId, getLoginAdmin())){
			return ajax(Status.error, "你无权修改数据!");
		}*/
		HSSFWorkbook hssfWorkbook;
		String fieldId = "";//分类字段id
		ContractCategory contractCategory = null;//二级分类
		try {
			Admin loginAdmin  = this.getLoginAdmin();//当前用户
			contractCategory = contractCategoryService.get(contractCategoryId);//获取二级分类
			logInfo = "导入二级分类"+contractCategory.getName();
			hssfWorkbook = new HSSFWorkbook(new FileInputStream(updateContract));
			//String fields = this.getRequest().getParameter("userId");
			String fields = loginAdmin.getId();//用户id
			fieldId = contractService.uploadContract(contractCategoryId, hssfWorkbook,loginAdmin);
		}catch(PersonalException e){
			logInfo = "批量导入"+e.getMessage();
  			return ajax(Status.error,e.getMessage());
  		}catch (Exception e) {
			logInfo = e.getMessage();
			//e.printStackTrace();
//			return ajax(Status.error,e.getMessage());
			return ajax(Status.error,"上传文件失败！请上传Excle文件");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", Status.success);
		map.put("message", "上传成功!");
		map.put("fieldId", fieldId);
		if(contractCategory != null){
			map.put("isCheck", contractCategory.getApprovalNeeded());//是否需要审核
		}
		return ajax(map);
	}

	
	public String updateContractCategory(){

		HSSFWorkbook hssfWorkbook;
		CheckInfo parseInfo = new CheckInfo();
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		String _updateContractFileName = updateContractFileName.replaceAll("(.*)\\.(\\w)+", "$1");
		
		data.put("fileName", _updateContractFileName);
		ArrayList<String> titleList = new ArrayList<String>();
all:	do{
			try {
				hssfWorkbook = new HSSFWorkbook(new FileInputStream(updateContract));
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
				if(hssfSheet==null){
					parseInfo.addError("sheet 为空");
					break all;
				}
				HSSFRow titleRow = hssfSheet.getRow(0);
				if(titleRow==null){
					parseInfo.addError("标题栏 为空");
					break all;
				}
				int coloumNum = titleRow.getPhysicalNumberOfCells();
				if(coloumNum==0){
					parseInfo.addError("标题栏 为空");
					break all;
				}
				for(int i = 0;; i++){
					HSSFCell cell = titleRow.getCell(i);
					String title = com.rzrk.util.StringUtils.parseExcel(cell);
					if(StringUtils.isEmpty(title)){
						break;
					}else{
						titleList.add(title);
					}
				}
			}catch (Exception e) {
				parseInfo.addError(e.getMessage());
			}
			data.put("titleList", new ArrayList<String>(new LinkedHashSet<String>(titleList)));
		}while(false);
		
		if(parseInfo.isError()){
			return ajax(Status.error, parseInfo.errorMessage());
		}else{
			return ajax(Status.success, "上传成功!",data);
		}
	}
	
//	public String uploadContractCategoryAndData(){
//		String _updateContractFileName = updateContractFileName.replaceAll("(.*)\\.(\\w)+", "$1");
//		HSSFWorkbook hssfWorkbook;
//		try {
//			Admin loginAdmin  = this.getLoginAdmin();//当前用户
//			hssfWorkbook = new HSSFWorkbook(new FileInputStream(updateContract));
//			contractService.uploadContractCategoryAndData(_updateContractFileName, hssfWorkbook,loginAdmin);
//		}catch (Exception e) {
//			return ajax(Status.error,  e.getMessage());
//		}
//		return ajax(Status.success, "上传成功!");
//	}
}
