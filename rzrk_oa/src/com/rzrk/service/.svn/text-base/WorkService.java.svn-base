/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ApprovalRecord;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkFlowPoint;
import com.rzrk.vo.contract.ContractEntity;


/**
 * 工作流类型接口 
 * @author kang.liu
 */

public interface WorkService extends BaseService<Work, String> {
	
	/**
	 * 检查我的工作名称是否唯一
	 * @return
	 */
	public List<Work> checkWorkName(String workName);
	
/*	*//**
	 *  查询我的工作 代办工作
	 * @param pager 分页参数
	 * @param param 查询条件参数
	 * @return
	 *//*
	public List<Work> getWorkList(Pager pager,Map<String,Object> param);*/
	
	/**
	 * 删除字段数据
	 * @param fieldList
	 */
	public void deleteContartField(Set<ContractField> fieldList,String workId);
	
	/**
	 * 删除工作流节点
	 */
	public void deleteWorkFlowId(Set<WorkFlowPoint> pointList);
	/**
	 * 增加流程实例时，附带二级类型的表单录入
	 * @param work
	 * @param contractCategoryId
	 * @param entity
	 * @throws Exception
	 */
	public void saveWithForm(Work work,String contractCategoryId, ContractEntity<String, String> entity);
	/**
	 * 修改流程实例时，附带二级类型的表单修改
	 * @param work
	 * @param idenValue
	 * @param contractCategoryId
	 * @param entity
	 * @throws Exception
	 */
	public void updateWithForm(Work work,String contractCategoryId, ContractEntity<String, String> entity);
	/**
	 * 新增我的工作
	 * @param work
	 * @param fileName
	 * @param filePath
	 * @param fieldId
	 * @param loginAdmin
	 * @param adminList
	 * @return
	 */
	public String insertWork(Work work,String[]  fileName,String[]  filePath,String fieldId,Admin loginAdmin,List<Admin>  adminList);
	
	/**
	 * 驳回我的工作
	 * @param work
	 * @param loginAdmin
	 * @param record
	 * @return
	 */
	public String quitWorkCheck(Work work,Admin loginAdmin, ApprovalRecord record,String type);
	
	/**
	 * 取消我的工作
	 * @param id
	 * @param loginAdmin
	 * @return
	 */
	public String cancleMyWork(String id,Admin loginAdmin);
	
	/**
	 * 审批通过
	 * @param work
	 * @param loginAdmin
	 * @param record
	 * @param adminList
	 * @return
	 */
	 public String updateWorkCheck(Work work,Admin loginAdmin,ApprovalRecord record,List<Admin>  adminList);
	 
	 /**
	  * 更新我的工作
	  * @param work
	  * @param temp
	  * @param adminList
	  * @return
	  */
	 public String updateWork(Work work,Work temp,List<Admin>  adminList,Admin loginAdmin,String[]  fileName,String[]  filePath);
	 
	 /**
	  * 获取优先级
	  * @param admin
	  * @return
	  */
	 public String getPriorityLevelByUser(Admin admin);
	 
	 /**
	  * 获取工作名称Set集合
	  * @return
	 */
	 public Set<String>  getJonSet();
	 
	    /**
		 * 根据条件获取当前用户
		 * @param work  我的工作
		 * @param tempPoint 当前节点
		 * @throws Exception
		 */
	public void getUserByCondition(Work work,WorkFlowPoint tempPoint,List<Admin> recepList,Admin loginAdmin);
	
	public void findLastPoint(Work work,WorkFlowPoint point,List<Admin> recepList,Admin loginAdmin);
	
	
	public List<Work> getByContractFieldPrimary(ContractField primaryField);
	
	 /**
     * 获取工作类型json数据
     * @return
     */
    public JSONArray getWorkFlowTypeList();
    
    /**
     * 获取工作流程json数据
     * @return
     */
    public JSONArray getWorkFlowList();
    
    /**
	 * 获取工作流表单的联动数据
	 * @return
	 */
	public  Map<String,Object> getAjaxWorkFlowDataByWorkFlowId(String flowTypeId,Long version);
	
	
   /**
	 * 获取工作流表单的联动数据
	 * @return
	 */
	public  Map<String,Object> getAjaxWorkFlowDataByWorkFlowId(String flowTypeId);
	public Map<String,Object> getRequestAjaxWorkList(Pager pager,String flowType,Admin loginAdmin);
	public Map<String,Object> getAjaxWorkList(Pager pager,String status,Admin loginAdmin);
}