/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rzrk.common.Returns.Return2;
import com.rzrk.common.Returns.Return3;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.Product;
import com.rzrk.entity.WorkFlowForm;
import com.rzrk.vo.contract.Field;


/**
 * Service接口 - 文章分类
 */

public interface ContractCategoryService extends BaseService<ContractCategory, String> {
	
	public String getContractFieldParserNames();
	
	public HSSFWorkbook getDownloadTemp(String id,Admin admin);
	
	public void deleteFromId(String id);

	public void saveWithCheck(ContractCategory contractCategory);

	public boolean isExistByCategoryname(String name);

	public Boolean canUpdateContractRecord(String contractCategoryId, Admin admin);
	
//	public boolean deleteWithCheck(String  contractCategoryId);
	
	public Return3<Boolean, String,String> deletesWithCheck(String[]  contractCategoryIds);

	public List<String> getExpressionScriptList(String id);
	
	public List<Field> getPermissionField(ContractCategory  contractCategory, Admin admin);
	
	public List<WorkFlowForm> getWorkFlowFormList(ContractCategory  contractCategory);

	public List<ContractCategory> getContentProviders();
	
	public void saveOrUpdate(ContractCategory ContractCategory,Collection<ContractCategory> childList);
	/**
	 * 获取二级分类下某个字段的所有值
	 * @param contractCategoryId
	 * @param feildName
	 * @return
	 */
	public List<String> getFiledValueList(String contractCategoryId, String feildName);
	public ContractCategory getContractCategoryByCategoryname(String name);
}