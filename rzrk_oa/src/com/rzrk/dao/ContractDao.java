package com.rzrk.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.vo.contract.ContractEntity;
import com.rzrk.vo.querytree.Node;

public interface ContractDao extends BaseDao<ContractField, String> {
	public Map<String,Object>  findPager(String contractCategoryId,Pager pager,Node node);
	public Map<String,Object>  findPager(String contractCategoryId,Pager pager,Node node,Map<String,Object> rowCondMap);
	@Deprecated
	public List<ContractField> getContractFields(String contractCategoryId,String[] fieldArr);
	public List<ContractField> getContractField(String rowId,String[] fieldArr);
	public Set<ContractField> save4work(String contractCategoryId,ContractEntity<String, String> entity);
	public String save(String parentRowId,String tempRowId, String contractCategoryId,ContractEntity<String, String> entity,Admin adminList);
	public String save(String parentRowId,String tempRowId, String contractCategoryId,ContractEntity<String, String> entity,Admin adminList,boolean useWork);
	public String update(String parentRowId,String rowId, String contractCategoryId,ContractEntity<String, String> entity,Admin adminList);
	public String update4work(String contractCategoryId,ContractEntity<String, String> entity);
	@Deprecated
	public void saveOrUpdate(String contractCategoryId,ContractEntity<String, String> entity);
	public String saveOrUpdateExt(String contractCategoryId,ContractEntity<String, String> entity,Admin loginAdmin);
	public void deleteForRowIds(String contractCategoryId, String[] rowIds);
	public void deleteFromContractCategoryId(String contractCategoryId);
	public List<String> selectForRowName(String contractCategoryId, String[] rowIds);
	/**
	 * 获取字段的集合
	 * @param contractCategoryId
	 * @param rowId
	 */
	public  Map<String,Object> getFieldList(String contractCategoryId, String rowId);
	
	/**
	 * 创建工作
	 * @param fieldId
	 * @param loginAdmin
	 * @param category
	 * @param isDelete
	 * @param resultList
	 * @param title
	 * @throws Exception
	 */
	public void createWork(String fieldId,Admin loginAdmin,ContractCategory category,String isDelete,List<ContractEntity<String, String>> resultList ,String title);
	
	/**
	 * 查询主键的数据
	 * @param contractCategoryId
	 * @return
	 */
	public List<ContractField> getContractPrimaryRows(String contractCategoryId);
	
	/**
	 * 查询一列的数据
	 * @param contractCategoryId
	 * @param fieldName
	 * @return
	 */
	public List<ContractField> getFieldListByColumn(String contractCategoryId,String fieldName);
	
	 /**
     * 更新项目关联
     * @param fieldString
     */
	@Deprecated
	public int  updateProjectRelation(String fieldString);
    /**
     * 查找用户计划关联的记录
     * @param userPlanId
     * @return
     */
	public List<Map> getUserPlanRelation(String userPlanId);
	
	/**
	 * 查找和项目相关的记录
	 * @param projectId
	 * @return
	 */
	public List<Map> getProjectRelation(String projectId);

}