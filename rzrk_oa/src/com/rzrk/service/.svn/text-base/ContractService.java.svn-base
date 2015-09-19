/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Product;
import com.rzrk.vo.contract.ContractEntity;
import com.rzrk.vo.contract.Field;
import com.rzrk.vo.querytree.Node;


/**
 * Service接口 - 文章分类
 */

public interface ContractService extends BaseService<ContractField, String> {
	@Deprecated
	public LinkedHashMap<String, HashMap<String, String>> getContractRows(String ccid,String [] titleArr);
	public ContractEntity<String, String> getContractRow(String rowId,String ccid,String[] titleArr);
	public String uploadContract(String contractCategoryId,HSSFWorkbook hssfWorkbook,Admin loginAdmin);
//	public void uploadContractCategoryAndData(String contractCategoryName,HSSFWorkbook hssfWorkbook,Admin loginAdmin) throws Exception;
	public List<String> selectForRowName(String contractCategoryId, String[] rowIds);
	public void deleteForRowIds(String contractCategoryId, String[] rowIds);
	public HSSFWorkbook getDownload(String contractCategoryId,String[] titleArr);
//	public String save4work(String contractCategoryId,LinkedHashMap<String, String> entity) throws Exception;
	public String save(String parentRowId,String tempRowId,String contractCategoryId,ContractEntity<String, String> entity,Admin adminList);
	public String update(String parentRowId,String rowId,String contractCategoryId,ContractEntity<String, String> entity,Admin adminList);
	/**
	 * 查询rowId 的字段
	 * @param rowId
	 * @return
	 */
	public List<ContractField> getContractFieldList(String rowId);
	/**
	 * 获取需要删除的id
	 * @param contractCategoryId
	 * @param indicates
	 */
	public String getFieldList(String contractCategoryId, String[] indicates,Admin loginAdmin) throws Exception;
	/**
	 * 分页查询结果，带权限
	 * @param pager
	 * @param contractCategoryId
	 * @param node
	 * @return
	 */
	public Pager findPager(Pager pager, String contractCategoryId,Node node );
	public Pager findPager(Pager pager, String contractCategoryId,Node node ,Map<String,Object> rowCondMap);
	/**
	 * 获取有权限的列
	 * @param admin
	 * @return
	 */
	public List<Field> getPermissionField(ContractCategory contractCategory, Admin admin);
	/**
	 * 获取一列的数据
	 * @param contractCategoryId
	 * @param fieldName
	 * @return
	 */
	public List<ContractField> getFieldListByColumn(String contractCategoryId,String fieldName);
	@Deprecated
	public int  updateProjectRelation(String fieldString);
	
	public void joinProject(String rowId,String projectId,Admin admin);
	
	public List<Map> getUserPlanRelation(String userPlanId);
	/**
	 * 查找和项目相关的记录
	 * @param projectId
	 * @return
	 */
	public List<Map> getProjectRelation(String projectId);

}